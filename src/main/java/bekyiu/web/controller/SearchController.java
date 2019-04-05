package bekyiu.web.controller;

import bekyiu.domain.Question;
import bekyiu.domain.ViewObject;
import bekyiu.service.IFollowService;
import bekyiu.service.IQuestionService;
import bekyiu.service.ISearchService;
import bekyiu.service.IUserService;
import bekyiu.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController
{
    @Autowired
    private ISearchService searchService;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFollowService followService;
    @RequestMapping("/search")
    public String search(Model model, String keyword, @RequestParam(defaultValue = "0") int offset,
                         @RequestParam(defaultValue = "20") int count)
    {
        try
        {
            List<Question> questions = searchService.searchQuestion(keyword, offset, count, "<em>", "</em>");
            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questions)
            {
                Question q = questionService.get(question.getId());
                if(question.getContent() != null)
                {
                    q.setContent(question.getContent());
                }
                if(question.getTitle() != null)
                {
                    q.setTitle(question.getTitle());
                }
                ViewObject vo = new ViewObject();
                vo.put("question", q);
                vo.put("askUser", userService.get(q.getUserId()));
                vo.put("followCount", followService.getFollowerCount(q.getId(), EntityType.ENTITY_QUESTION));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "result";
    }
}

package bekyiu.web.controller;

import bekyiu.domain.Question;
import bekyiu.domain.ViewObject;
import bekyiu.service.IQuestionService;
import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController
{
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private IUserService userService;

    @RequestMapping({"/", "/index"})
    public String index(Model model)
    {
        List<Question> questions = questionService.getLatestQuestions(0, 0, 5);
        model.addAttribute("vos", getQuestionsAndUsers(questions));
        return "index";
    }

    @RequestMapping("/user/{userId}")
    public String userIndex(@PathVariable("userId") Integer userId, Model model)
    {
        List<Question> questions = questionService.getLatestQuestions(userId, 0, 5);
        model.addAttribute("vos", getQuestionsAndUsers(questions));
        return "index";
    }

    private List<ViewObject> getQuestionsAndUsers(List<Question> questions)
    {
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questions)
        {
            ViewObject vo = new ViewObject();
            vo.put("question", question);
            vo.put("user", userService.get(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}

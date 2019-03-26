package bekyiu.web.controller;

import bekyiu.domain.Comment;
import bekyiu.domain.HostHolder;
import bekyiu.domain.Question;
import bekyiu.domain.ViewObject;
import bekyiu.service.ICommentService;
import bekyiu.service.ILikeService;
import bekyiu.service.IQuestionService;
import bekyiu.service.IUserService;
import bekyiu.util.EntityType;
import bekyiu.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController
{
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IQuestionService questionService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ILikeService likeService;

    @RequestMapping("/question/add")
    @ResponseBody
    public String release(String title, String content)
    {
        Question question = new Question();
        question.setContent(content);
        question.setTitle(title);
        question.setCreateDate(new Date());
        question.setCommentCount(0);
        if (hostHolder.getUser().getId() == null)
        {
            return JsonUtil.getJsonString(1, "请先登陆");
        }
        else
        {
            question.setUserId(hostHolder.getUser().getId());
        }
        questionService.save(question);
        return JsonUtil.getJsonString(0);
    }

    @RequestMapping("/question/{questionId}")
    public String questionDetail(@PathVariable Long questionId, Model model)
    {
        Question question = questionService.get(questionId);
        model.addAttribute("question", question);
        model.addAttribute("askUser", userService.get(question.getUserId()));
        List<Comment> comments = commentService.selectCommentByEntity(EntityType.ENTITY_QUESTION, questionId);
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : comments)
        {
            ViewObject vo = new ViewObject();
            vo.put("comment", comment);
            vo.put("user", userService.get(comment.getUserId()));
            vo.put("likeCount", likeService.getLikeCount(comment.getId(), EntityType.ENTITY_COMMENT));
            if (hostHolder.getUser().getId() == null)
            {
                vo.put("likeStatus", 0);
            }
            else
            {
                vo.put("likeStatus", likeService.getLikeStatus(hostHolder.getUser().getId(),
                        comment.getId(), EntityType.ENTITY_COMMENT));
            }
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "detail";
    }

}

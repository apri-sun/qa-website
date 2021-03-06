package bekyiu.web.controller;

import bekyiu.domain.Comment;
import bekyiu.domain.HostHolder;
import bekyiu.domain.Question;
import bekyiu.domain.ViewObject;
import bekyiu.service.*;
import bekyiu.util.EntityType;
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
    @Autowired
    private IFollowService followService;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping({"/", "/index"})
    public String index(Model model)
    {
        List<Question> questions = questionService.getLatestQuestions(0, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questions)
        {
            ViewObject vo = new ViewObject();
            vo.put("question", question);
            vo.put("user", userService.get(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        model.addAttribute("isShowUserInfo", false);
        return "index";
    }

    @RequestMapping("/user/{userId}")
    public String userIndex(@PathVariable("userId") Long userId, Model model)
    {
        List<Question> questions = questionService.getLatestQuestions(userId.intValue(), 0, 10);

        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questions)
        {
            ViewObject vo = new ViewObject();
            vo.put("question", question);
            vo.put("user", userService.get(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        model.addAttribute("isShowUserInfo", true);
        model.addAttribute("thisUser", userService.get(userId));
        model.addAttribute("isFollowedByMe", followService.isFollower(hostHolder.getUser().getId(), userId, EntityType.ENTITY_USER));
        model.addAttribute("followerCount", followService.getFollowerCount(userId, EntityType.ENTITY_USER));
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId, EntityType.ENTITY_USER));

        //这个user一共收到过多少个赞
        model.addAttribute("commentNum", commentService.getCommentCountByUserId(userId));
        model.addAttribute("likeCount", likeService.getSelfLikeCount(userId));
        //这个user一共提过几次问题
        model.addAttribute("askCount", questionService.getQuestionByUserId(userId).size());
        return "index";
    }

//    private List<ViewObject> getQuestionsAndUsers(List<Question> questions)
//    {
//        List<ViewObject> vos = new ArrayList<>();
//        for (Question question : questions)
//        {
//            ViewObject vo = new ViewObject();
//            vo.put("question", question);
//            vo.put("user", userService.get(question.getUserId()));
//            vos.add(vo);
//        }
//        return vos;
//    }
}

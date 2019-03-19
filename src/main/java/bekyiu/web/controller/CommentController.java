package bekyiu.web.controller;

import bekyiu.domain.Comment;
import bekyiu.domain.HostHolder;
import bekyiu.domain.User;
import bekyiu.service.ICommentService;
import bekyiu.service.IQuestionService;
import bekyiu.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class CommentController
{
    @Autowired
    private ICommentService commentService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private IQuestionService questionService;

    @RequestMapping("/addComment")
    //给问题添加评论
    public String addComment(Long questionId, String content)
    {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setEntityId(questionId);
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        if (hostHolder.getUser().getId() == null)
        {
            comment.setUserId(User.ANONYMOUS_USER_ID);
        }
        else
        {
            comment.setUserId(hostHolder.getUser().getId());
        }
        commentService.save(comment);
        Integer count = commentService.getCommentCount(EntityType.ENTITY_QUESTION, questionId);
        questionService.updateCommentCount(questionId, count);
        return "redirect:/question/" + questionId;
    }
}

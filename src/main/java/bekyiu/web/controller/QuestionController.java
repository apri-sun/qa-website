package bekyiu.web.controller;

import bekyiu.domain.HostHolder;
import bekyiu.domain.Question;
import bekyiu.service.IQuestionService;
import bekyiu.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class QuestionController
{
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IQuestionService questionService;

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
            question.setUserId(1L);
        }
        else
        {
            question.setUserId(hostHolder.getUser().getId());
        }
        questionService.save(question);
        return JsonUtil.getJsonString(0);
    }
}

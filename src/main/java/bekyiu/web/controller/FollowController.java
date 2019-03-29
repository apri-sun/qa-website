package bekyiu.web.controller;

import bekyiu.async.EventModel;
import bekyiu.async.EventProducer;
import bekyiu.async.EventType;
import bekyiu.domain.HostHolder;
import bekyiu.service.IFollowService;
import bekyiu.service.IQuestionService;
import bekyiu.service.IUserService;
import bekyiu.util.EntityType;
import bekyiu.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class FollowController
{
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private IFollowService followService;
    @Autowired
    private IUserService userService;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private IQuestionService questionService;

    @RequestMapping("/followUser")
    @ResponseBody
    public String followUser(Long userId)
    {
        if(hostHolder.getUser().getUsername() == null)
        {
            return JsonUtil.getJsonString(1);
        }
        Boolean ret = followService.follow(hostHolder.getUser().getId(), userId, EntityType.ENTITY_USER);
        eventProducer.fireEvent(new EventModel()
                .setType(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId())
                .setEntityOwnerId(userId)
                .setEntityId(userId)
                .setEntityType(EntityType.ENTITY_USER));

        //
        return getBackInfo(userId, EntityType.ENTITY_USER, ret);
    }

    @RequestMapping("/unfollowUser")
    @ResponseBody
    public String unfollowUser(Long userId)
    {
        if(hostHolder.getUser().getUsername() == null)
        {
            return JsonUtil.getJsonString(1);
        }
        Boolean ret = followService.unfollow(hostHolder.getUser().getId(), userId, EntityType.ENTITY_USER);
        eventProducer.fireEvent(new EventModel()
                .setType(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId())
                .setEntityOwnerId(userId)
                .setEntityId(userId)
                .setEntityType(EntityType.ENTITY_USER));
        //
        return getBackInfo(userId, EntityType.ENTITY_USER, ret);
    }

    @RequestMapping("/followQuestion")
    @ResponseBody
    public String followQuestion(Long questionId)
    {
        if(hostHolder.getUser().getUsername() == null)
        {
            return JsonUtil.getJsonString(1);
        }
        Boolean ret = followService.follow(hostHolder.getUser().getId(), questionId, EntityType.ENTITY_QUESTION);
        eventProducer.fireEvent(new EventModel()
                .setType(EventType.FOLLOW_QUESTION)
                .setActorId(hostHolder.getUser().getId())
                .setEntityOwnerId(questionService.get(questionId).getUserId())
                .setEntityId(questionId)
                .setEntityType(EntityType.ENTITY_QUESTION)
                .setExts("questionId", String.valueOf(questionId)));

        return getBackInfo(questionId, EntityType.ENTITY_QUESTION, ret);
    }

    @RequestMapping("/unfollowQuestion")
    @ResponseBody
    public String unfollowQuestion(Long questionId)
    {
        if(hostHolder.getUser().getUsername() == null)
        {
            return JsonUtil.getJsonString(1);
        }

        Boolean ret = followService.unfollow(hostHolder.getUser().getId(), questionId, EntityType.ENTITY_QUESTION);
        eventProducer.fireEvent(new EventModel()
                .setType(EventType.UNFOLLOW_QUESTION)
                .setActorId(hostHolder.getUser().getId())
                .setEntityOwnerId(questionService.get(questionId).getUserId())
                .setEntityId(questionId)
                .setEntityType(EntityType.ENTITY_QUESTION)
                .setExts("questionId", String.valueOf(questionId)));

        return getBackInfo(questionId, EntityType.ENTITY_QUESTION, ret);
    }

    //跳转到粉丝列表
    @RequestMapping("/user/{userId}/followers")
    public String followerList(Model model, @PathVariable Long userId)
    {
        return "followList";
    }


    //关注人/问题 后应该返回的信息
    private String getBackInfo(Long entityId, Integer entityType, Boolean ret)
    {
        Map<String, Object> json = new HashMap<>();
        json.put("code", ret ? 0 : 1);
        json.put("followerCount", followService.getFollowerCount(entityId, entityType));
        if(entityType.equals(EntityType.ENTITY_QUESTION))
        {
            json.put("me", hostHolder.getUser());
        }
        return JSON.toJSONString(json);
    }

}

package bekyiu.web.controller;

import bekyiu.async.EventModel;
import bekyiu.async.EventProducer;
import bekyiu.async.EventType;
import bekyiu.domain.Comment;
import bekyiu.domain.HostHolder;
import bekyiu.domain.User;
import bekyiu.domain.ViewObject;
import bekyiu.service.*;
import bekyiu.util.EntityType;
import bekyiu.util.JsonUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ILikeService likeService;

    @RequestMapping("/followUser")
    @ResponseBody
    public String followUser(Long userId)
    {
        if (hostHolder.getUser().getUsername() == null)
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
        if (hostHolder.getUser().getUsername() == null)
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
        if (hostHolder.getUser().getUsername() == null)
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
        if (hostHolder.getUser().getUsername() == null)
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

    //跳转到这个userId的粉丝列表
    @RequestMapping("/user/{userId}/followers")
    public String followerList(Model model, @PathVariable Long userId)
    {
        List<Integer> followersId = followService.getFollowers(userId, EntityType.ENTITY_USER, 0L, -1L);
        model.addAttribute("vos", getVos(true, followersId, userId));
        model.addAttribute("curUser", userService.get(userId));
        model.addAttribute("followerCount", followersId.size());
        //表示当前页面是粉丝列表
        model.addAttribute("isFollower", true);
        return "followList";
    }

    //跳转到这个userId关注的人的列表
    @RequestMapping("/user/{userId}/followees")
    public String followeeList(Model model, @PathVariable Long userId)
    {
        List<Integer> followeesId = followService.getFollowees(userId, EntityType.ENTITY_USER, 0L, -1L);
        model.addAttribute("vos", getVos(false, followeesId, userId));
        model.addAttribute("curUser", userService.get(userId));
        model.addAttribute("followerCount", followeesId.size());
        //表示当前页面是关注列表
        model.addAttribute("isFollower", false);
        return "followList";
    }

    //关注人/问题 后应该返回的信息
    private String getBackInfo(Long entityId, Integer entityType, Boolean ret)
    {
        Map<String, Object> json = new HashMap<>();
        json.put("code", ret ? 0 : 1);
        json.put("followerCount", followService.getFollowerCount(entityId, entityType));
        if (entityType.equals(EntityType.ENTITY_QUESTION))
        {
            json.put("me", hostHolder.getUser());
        }
        return JSON.toJSONString(json);
    }

    //跳到 粉丝/关注人 列表需要的vos (每一行需要的数据)
    private List<ViewObject> getVos(Boolean isFollowerList, List<Integer> ids, Long userId)
    {
        List<ViewObject> vos = new ArrayList<>();
        for (Integer id : ids)
        {
            ViewObject vo = new ViewObject();
            User user = userService.get((long) id);
            vo.put("follower", user);

            vo.put("followerCount", followService.getFollowerCount(user.getId(), EntityType.ENTITY_USER));
            vo.put("followeeCount", followService.getFolloweeCount(user.getId(), EntityType.ENTITY_USER));

            vo.put("askCount", questionService.getQuestionByUserId(user.getId()).size());

            vo.put("commentNum", commentService.getCommentCountByUserId(user.getId()));
            //这个user一共收到过多少个赞
            vo.put("likeCount", likeService.getSelfLikeCount(user.getId()));

            if (isFollowerList)
            {
                //如果来到的是自己的粉丝列表 就要看下自己是否关注了这些粉丝
                if (userId.equals(hostHolder.getUser().getId()))
                {
                    vo.put("isFollowedByMe", followService.isFollower(hostHolder.getUser().getId(), user.getId(), EntityType.ENTITY_USER));
                }
            }
            else
            {
                //如果来到的是自己的关注列表
                if (userId.equals(hostHolder.getUser().getId()))
                {
                    vo.put("isFollowedByMe", true);
                }
            }
            vos.add(vo);
        }
        return vos;
    }
}

package bekyiu.web.controller;

import bekyiu.async.EventModel;
import bekyiu.async.EventProducer;
import bekyiu.async.EventType;
import bekyiu.domain.HostHolder;
import bekyiu.service.IFollowService;
import bekyiu.service.IUserService;
import bekyiu.util.EntityType;
import bekyiu.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        // 返回关注的人数
        return JsonUtil.getJsonString(ret ? 0 : 1,
                followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER) + "");
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
        // 返回关注的人数
        return JsonUtil.getJsonString(ret ? 0 : 1,
                followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.ENTITY_USER) + "");
    }
}

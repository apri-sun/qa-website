package bekyiu.async.handler;

import bekyiu.async.EventHandler;
import bekyiu.async.EventModel;
import bekyiu.async.EventType;
import bekyiu.domain.Feed;
import bekyiu.domain.Question;
import bekyiu.domain.User;
import bekyiu.service.IFeedService;
import bekyiu.service.IQuestionService;
import bekyiu.service.IUserService;
import bekyiu.util.EntityType;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedHandler implements EventHandler
{
    @Autowired
    private IFeedService feedService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IQuestionService questionService;

    private String buildFeedData(EventModel eventModel)
    {
        Map<String, Object> json = new HashMap<>();
        //触发用户
        User actor = userService.get(eventModel.getActorId());
        if(actor == null)
        {
            return null;
        }
        json.put("actorUser", actor);
        //如果当前事件是评论, 并且评论的是问题 或者 如果当前事件是关注, 并且关注的是问题
        if (eventModel.getEntityType().equals(EntityType.ENTITY_QUESTION) &&
                (eventModel.getType().equals(EventType.COMMENT) || eventModel.getType().equals(EventType.FOLLOW_QUESTION)))
        {
            Question question = questionService.get(eventModel.getEntityId());
            if(question == null)
            {
                return null;
            }
            //comment or follow question
            json.put("cofQuestion", question);
            return JSON.toJSONString(json);
        }
        return null;
    }

    @Override
    public void doHandle(EventModel eventModel)
    {
        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setUserId(eventModel.getActorId());
        feed.setType(eventModel.getType().getValue());
        feed.setData(buildFeedData(eventModel));
        if(feed.getData() == null)
        {
            return;
        }
        feedService.save(feed);
    }

    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.COMMENT, EventType.FOLLOW_QUESTION);
    }
}

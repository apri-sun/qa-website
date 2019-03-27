package bekyiu.async.handler;

import bekyiu.async.EventHandler;
import bekyiu.async.EventModel;
import bekyiu.async.EventType;
import bekyiu.domain.Message;
import bekyiu.service.IMessageService;
import bekyiu.service.IUserService;
import bekyiu.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
//点赞事件, 需要给被点赞的人发私信
@Component
public class LikeHandler implements EventHandler
{
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IUserService userService;
    @Override
    public void doHandle(EventModel eventModel)
    {
        Message message = new Message();
        message.setFromId(EntityType.SYSTEM_USER_ID);
        message.setToId(eventModel.getEntityOwnerId());
        message.setHasRead(EntityType.NOT_READ);
        message.setCreatedDate(new Date());
        message.setContent("用户: " + userService.get(eventModel.getActorId()).getUsername() + "赞了你的评论 "
                + "http://127.0.0.1:8080/question/" + eventModel.getExts("questionId"));
        messageService.save(message);

    }

    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.LIKE);
    }
}

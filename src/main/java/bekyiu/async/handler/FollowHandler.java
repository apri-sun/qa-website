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

@Component
public class FollowHandler implements EventHandler
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

        if (eventModel.getType().equals(EventType.FOLLOW))
        {

            message.setContent("用户: " + userService.get(eventModel.getActorId()).getUsername() +
                    "关注了你");
            messageService.save(message);
        }
        else if (eventModel.getType().equals(EventType.UNFOLLOW))
        {
            message.setContent("用户: " + userService.get(eventModel.getActorId()).getUsername() +
                    "取关了你");
            messageService.save(message);
        }
    }

    @Override
    public List<EventType> getSupportEventTypes()
    {
        return Arrays.asList(EventType.FOLLOW, EventType.UNFOLLOW);
    }
}

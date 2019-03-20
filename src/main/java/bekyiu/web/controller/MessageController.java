package bekyiu.web.controller;

import bekyiu.domain.HostHolder;
import bekyiu.domain.Message;
import bekyiu.domain.User;
import bekyiu.service.IMessageService;
import bekyiu.service.IUserService;
import bekyiu.util.EntityType;
import bekyiu.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class MessageController
{
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMessageService messageService;
    @RequestMapping("/msg/list")
    public String messageList()
    {
        return "letter";
    }

    @RequestMapping("/msg/detail")
    public String messageDetail(String conversationId)
    {
        return "letterDetail";
    }

    @RequestMapping("/msg/add")
    @ResponseBody
    public String addMessage(String toName, String content)
    {
        if(hostHolder.getUser() == null || hostHolder.getUser().getId() == null)
        {
            return JsonUtil.getJsonString(1, "请先登录");
        }
        User toUser = userService.selectByUsername(toName);
        if(toUser == null)
        {
            return JsonUtil.getJsonString(1, "发送用户不存在");
        }
        Message message = new Message();
        message.setContent(content);
        message.setCreatedDate(new Date());
        message.setHasRead(EntityType.HAS_READ);
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(toUser.getId());
        //不用set, 因为set toid和fromid的时候相当于set了 get的时候回自动拼
        //message.setConversationId();
        messageService.save(message);
        return JsonUtil.getJsonString(0);
    }
}

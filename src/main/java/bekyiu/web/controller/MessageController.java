package bekyiu.web.controller;

import bekyiu.domain.HostHolder;
import bekyiu.domain.Message;
import bekyiu.domain.User;
import bekyiu.domain.ViewObject;
import bekyiu.service.IMessageService;
import bekyiu.service.IUserService;
import bekyiu.util.EntityType;
import bekyiu.util.JsonUtil;
import bekyiu.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public String messageList(Model model)
    {
        if(hostHolder.getUser() == null || hostHolder.getUser().getId() == null)
        {
            return JsonUtil.getJsonString(1, "请先登录");
        }
        List<MessageVO> conversations = messageService.getConversationList(hostHolder.getUser().getId(), 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (MessageVO conversation : conversations)
        {
            ViewObject vo = new ViewObject();
            vo.put("conversation", conversation);
            //对方的id
            Long targetId = conversation.getFromId() == hostHolder.getUser().getId() ? conversation.getToId() :  conversation.getFromId();
            vo.put("targetUser", userService.get(targetId));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "letter";
    }

    @RequestMapping("/msg/detail")
    public String messageDetail(Model model, String conversationId)
    {
        List<Message> messages = messageService.getByConversationId(conversationId, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messages)
        {
            ViewObject vo = new ViewObject();
            vo.put("message", message);
            vo.put("user", userService.get(message.getFromId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
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

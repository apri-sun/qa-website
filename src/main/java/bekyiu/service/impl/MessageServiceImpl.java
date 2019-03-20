package bekyiu.service.impl;

import bekyiu.domain.Message;
import bekyiu.mapper.MessageMapper;
import bekyiu.service.IMessageService;
import bekyiu.service.ISensitiveWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements IMessageService
{
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ISensitiveWordsService sensitiveWordsService;

    @Override
    public void save(Message message)
    {
        message.setContent(sensitiveWordsService.filter(message.getContent()));
        messageMapper.insert(message);
    }

    @Override
    public List<Message> getByConversationId(String conversationId, Integer offset, Integer limit)
    {
        return messageMapper.getByConversationId(conversationId, offset, limit);
    }
}

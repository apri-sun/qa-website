package bekyiu.service;

import bekyiu.domain.Message;

import java.util.List;

public interface IMessageService
{
    void save(Message message);

    List<Message> getByConversationId(String conversationId, Integer offset, Integer limit);
}

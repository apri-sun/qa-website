package bekyiu.mapper;

import bekyiu.domain.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MessageMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(Message message);

    int updateByPrimaryKey(Message message);

    Message selectByPrimaryKey(Long id);

    List<Message> selectAll();

    //查询出和同一个人的所有对话消息
    List<Message> getByConversationId(@Param("conversationId") String conversationId,
                                      @Param("offset") Integer offset,
                                      @Param("limit") Integer limit);
}
package bekyiu.mapper;

import bekyiu.domain.Message;
import bekyiu.vo.MessageVO;
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

    //查询出当前登录用户和所有人的对话列表(众多消息的最近条)
    // select *, count(id) as count from
    // (select * from message where from_id = ? or to_id = ? order by created_date desc) reverseTable
    //group by conversation_id order by created_date desc limit ?, ?;
    List<MessageVO> getConversationList(@Param("userId") Long userId,
                                        @Param("offset") Integer offset,
                                        @Param("limit") Integer limit);

    //拿到一个对话中未读的消息条数
    //select count(id) from message where to_id = #{userId}
    // and has_read = 0 and conversation_id = #{conversationId}
    Integer getUnReadCount(@Param("userId") Long userId, @Param("conversationId") String conversationId);

    //改变已读状态
    //update message set has_read = ? where conversation_id = ? and to_id = ?
    void updateReadStatus(@Param("hasRead") Integer hasRead,
                          @Param("userId") Long userId,
                          @Param("conversationId") String conversationId);
}
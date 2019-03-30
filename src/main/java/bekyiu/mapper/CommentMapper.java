package bekyiu.mapper;

import bekyiu.domain.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(Comment comment);

    Comment selectByPrimaryKey(Long id);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment comment);

    //比如查询的entityType是question, id是5, 就是查询5号问题的所有评论
    List<Comment> selectCommentByEntity(@Param("entityType") Integer entityType, @Param("entityId") Long entityId);

    Integer getCommentCount(@Param("entityType") Integer entityType, @Param("entityId") Long entityId);

    List<Comment> getByUserId(Long userId);

    /**
     * 查询出这个userId代表的用户 在entityId表示的问题下的所有回答(entityType默认是question)
     * 按时间先后排序
     * @param userId
     * @param entityId
     * @return
     */
    List<Comment> getByUserIdAndEntityId(@Param("userId") Long userId, @Param("entityId") Long entityId);
}
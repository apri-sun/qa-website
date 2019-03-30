package bekyiu.service;

import bekyiu.domain.Comment;

import java.util.List;

public interface ICommentService
{
    void update(Comment comment);

    void delete(Long id);

    void save(Comment comment);

    Comment get(Long id);

    List<Comment> listAll();

    //比如查询的entityType是question, id是5, 就是查询5号问题的所有评论
    List<Comment> selectCommentByEntity(Integer entityType, Long entityId);

    Integer getCommentCount(Integer entityType, Long entityId);

    //查询这个用户的所有评论
    List<Comment> getByUserId(Long userId);

    //查询这个用户回答问题的总数量
    Integer getCommentCountByUserId(Long userId);

    /**
     * 查询出这个userId代表的用户 在entityId表示的问题下的所有回答(entityType默认是question)
     * 中时间最新的那一个
     * @param userId
     * @param entityId
     * @return
     */
    Comment getByUserIdAndEntityId(Long userId, Long entityId);
}

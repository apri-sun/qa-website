package bekyiu.service;

public interface ILikeService
{
    /**
     *
     * @param entityId
     * @param entityType
     * @return 返回当前entity有多少人喜欢
     */
    Long getLikeCount(Long entityId, Integer entityType);

    /**
     * 返回这个用户对这个entity是否喜欢
     * @param userId
     * @param entityId
     * @param entityType
     * @return 1表示喜欢, -1表示不喜欢, 0表示没有操作过
     */
    Integer getLikeStatus(Long userId, Long entityId, Integer entityType);

    /**
     * 点赞
     * @param userId 这个用户
     * @param entityId 点赞的对象的id
     * @param entityType 点赞对象的类型
     * @return 返回点赞的总数量
     */
    Long addLike(Long userId, Long entityId, Integer entityType);

    /**
     * 点踩
     * @param userId
     * @param entityId
     * @param entityType
     */
    Long addDislike(Long userId, Long entityId, Integer entityType);

    /**
     * 查询这个用户的所有回答收到过多少个赞
     */
    Integer getSelfLikeCount(Long userId);
}

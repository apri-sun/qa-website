package bekyiu.service;

import java.util.List;

public interface IFollowService
{
    //当前用户关注一个实体
    Boolean follow(Long userId, Long entityId, Integer entityType);

    //当前用户取关一个实体
    Boolean unfollow(Long userId, Long entityId, Integer entityType);

    //获取当前实体的粉丝
    List<Integer> getFollowers(Long entityId, Integer entityType, Long start, Long count);

    //获取当前用户的关注对象
    List<Integer> getFollowees(Long userId, Integer entityType, Long start, Long count);

    //当前实体有多少个粉丝
    Long getFollowerCount(Long entityId, Integer entityType);

    //当前用户关注了某个类型的实体的个数
    Long getFolloweeCount(Long userId, Integer entityType);

    //当前用户是否是某个实体的粉丝
    Boolean isFollower(Long userId, Long entityId, Integer entityType);
}

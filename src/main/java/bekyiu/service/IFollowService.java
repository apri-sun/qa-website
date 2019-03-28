package bekyiu.service;

import java.util.List;

public interface IFollowService
{
    //当前用户关注一个实体
    Boolean follow(Long userId, Long entityId, Integer entityType);

    //当前用户取关一个实体
    Boolean unfollow(Long userId, Long entityId, Integer entityType);

    //获取当前实体的粉丝
    List<Integer> getFollowers(Long entityId, Integer entityType, Long start, Long stop);

    //获取当前用户的关注对象
    List<Integer> getFollowees(Long userId, Integer entityType, Long start, Long stop);
}

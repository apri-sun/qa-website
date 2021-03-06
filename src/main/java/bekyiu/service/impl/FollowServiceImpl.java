package bekyiu.service.impl;

import bekyiu.service.IFollowService;
import bekyiu.util.JedisAdapter;
import bekyiu.util.RedisKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowServiceImpl implements IFollowService
{
    @Autowired
    private JedisAdapter jedisAdapter;

    @Override

    //A关注B 首先要把A加入B的粉丝列表, 然后还要把B加入A的关注列表
    public Boolean follow(Long userId, Long entityId, Integer entityType)
    {
        //得到B的粉丝列表的key
        String followerKey = RedisKeyGenerator.getFollowerKey(entityId, entityType);
        //得到A关注列表的key
        String followeeKey = RedisKeyGenerator.getFolloweeKey(userId, entityType);
        Date date = new Date();
        try
        {
            jedisAdapter.zadd(followerKey, (double) date.getTime(), String.valueOf(userId));
            jedisAdapter.zadd(followeeKey, (double) date.getTime(), String.valueOf(entityId));
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    //A取关B
    public Boolean unfollow(Long userId, Long entityId, Integer entityType)
    {
        //得到B的粉丝列表的key
        String followerKey = RedisKeyGenerator.getFollowerKey(entityId, entityType);
        //得到A关注列表的key
        String followeeKey = RedisKeyGenerator.getFolloweeKey(userId, entityType);
        try
        {
            jedisAdapter.zrem(followerKey, String.valueOf(userId));
            jedisAdapter.zrem(followeeKey, String.valueOf(entityId));
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Integer> getFollowers(Long entityId, Integer entityType, Long start, Long count)
    {
        String followerKey = RedisKeyGenerator.getFollowerKey(entityId, entityType);
        Set<String> followers = jedisAdapter.zrevrange(followerKey, start, start + count);
        return set2List(followers);
    }

    @Override
    public List<Integer> getFollowees(Long userId, Integer entityType, Long start, Long count)
    {
        String followeeKey = RedisKeyGenerator.getFolloweeKey(userId, entityType);
        Set<String> followees = jedisAdapter.zrevrange(followeeKey, start, start + count);
        return set2List(followees);
    }

    @Override
    public Long getFollowerCount(Long entityId, Integer entityType)
    {
        String followerKey = RedisKeyGenerator.getFollowerKey(entityId, entityType);
        return jedisAdapter.zcard(followerKey);
    }

    @Override
    public Long getFolloweeCount(Long userId, Integer entityType)
    {
        String followeeKey = RedisKeyGenerator.getFolloweeKey(userId, entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    @Override
    public Boolean isFollower(Long userId, Long entityId, Integer entityType)
    {
        String followerKey = RedisKeyGenerator.getFollowerKey(entityId, entityType);
        Double score = jedisAdapter.zscore(followerKey, String.valueOf(userId));
        return score != null;
    }


    //
    private List<Integer> set2List(Set<String> set)
    {
        List<Integer> list = new ArrayList<>();
        for (String s : set)
        {
            list.add(Integer.valueOf(s));
        }
        return list;
    }
}

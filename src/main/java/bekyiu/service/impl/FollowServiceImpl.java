package bekyiu.service.impl;

import bekyiu.service.IFollowService;
import bekyiu.util.JedisAdapter;
import bekyiu.util.RedisKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

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
        //redis事务
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        jedisAdapter.zadd(followerKey, (double) date.getTime(), String.valueOf(userId));
        jedisAdapter.zadd(followeeKey, (double) date.getTime(), String.valueOf(entityId));
        List<Object> exec = jedisAdapter.exec(tx, jedis);
        return exec != null && exec.size() == 2 && exec.get(0) != null && exec.get(1) != null;
    }

    @Override
    //A取关B
    public Boolean unfollow(Long userId, Long entityId, Integer entityType)
    {
        //得到B的粉丝列表的key
        String followerKey = RedisKeyGenerator.getFollowerKey(entityId, entityType);
        //得到A关注列表的key
        String followeeKey = RedisKeyGenerator.getFolloweeKey(userId, entityType);
        //redis事务
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        jedisAdapter.zrem(followerKey, String.valueOf(userId));
        jedisAdapter.zrem(followeeKey, String.valueOf(entityId));
        List<Object> exec = jedisAdapter.exec(tx, jedis);
        return exec != null && exec.size() == 2 && exec.get(0) != null && exec.get(1) != null;
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

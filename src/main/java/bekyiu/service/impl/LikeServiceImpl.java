package bekyiu.service.impl;

import bekyiu.service.ILikeService;
import bekyiu.util.JedisAdapter;
import bekyiu.util.RedisKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements ILikeService
{
    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public Long getLikeCount(Long entityId, Integer entityType)
    {
        String likeKey = RedisKeyGenerator.getLikeKey(entityId, entityType);
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public Integer getLikeStatus(Long userId, Long entityId, Integer entityType)
    {
        String likeKey = RedisKeyGenerator.getLikeKey(entityId, entityType);
        if (jedisAdapter.sismember(likeKey, String.valueOf(userId)))
        {
            return 1;
        }
        String dislikeKey = RedisKeyGenerator.getDislikeKey(entityId, entityType);
        return jedisAdapter.sismember(dislikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    @Override
    public Long addLike(Long userId, Long entityId, Integer entityType)
    {
        String likeKey = RedisKeyGenerator.getLikeKey(entityId, entityType);
        String dislikeKey = RedisKeyGenerator.getDislikeKey(entityId, entityType);
        return addLikeOrDislike(userId, likeKey, dislikeKey);
    }

    @Override
    public Long addDislike(Long userId, Long entityId, Integer entityType)
    {
        String likeKey = RedisKeyGenerator.getLikeKey(entityId, entityType);
        String dislikeKey = RedisKeyGenerator.getDislikeKey(entityId, entityType);
        return addLikeOrDislike(userId, dislikeKey, likeKey);
    }

    private Long addLikeOrDislike(Long userId, String k1, String k2)
    {
        jedisAdapter.sadd(k1, String.valueOf(userId));
        //不能同时点赞和点踩
        jedisAdapter.srem(k2, String.valueOf(userId));
        //始终返回的是点赞的人数
        if (k1.contains(RedisKeyGenerator.BIZ_DISLIKE))
        {
            return jedisAdapter.scard(k2);
        }
        else
        {
            return jedisAdapter.scard(k1);
        }
    }
}

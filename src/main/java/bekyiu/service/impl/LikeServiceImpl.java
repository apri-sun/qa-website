package bekyiu.service.impl;

import bekyiu.service.ILikeService;
import bekyiu.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements ILikeService
{
    @Autowired
    JedisAdapter jedisAdapter;
    @Override
    public Long getLikeCount(Integer entityId, Integer entityType)
    {
        return null;
    }

    @Override
    public Integer getLikeStatus(Long userId, Integer entityId, Integer entityType)
    {
        return null;
    }

    @Override
    public Long addLike(Long userId, Integer entityId, Integer entityType)
    {
        return null;
    }

    @Override
    public Long addDislike(Long userId, Integer entityId, Integer entityType)
    {
        return null;
    }
}

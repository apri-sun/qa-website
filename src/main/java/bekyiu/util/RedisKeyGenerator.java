package bekyiu.util;

//用于集中生成redis的key, 便于管理
public class RedisKeyGenerator
{
    public static final String SPLIT = ":";
    public static final String BIZ_LIKE = "LIKE";
    public static final String BIZ_DISLIKE = "DISLIKE";
    public static final String BIZ_EVENT_QUEUE = "EVENT_QUEUE";
    public static final String BIZ_FOLLOWER = "FOLLOWER";
    public static final String BIZ_FOLLOWEE = "FOLLOWEE";

    //得到对于当前想点赞的对象的key
    public static String getLikeKey(Long entityId, Integer entityType)
    {
        return BIZ_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDislikeKey(Long entityId, Integer entityType)
    {
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getEventQueueKey()
    {
        return BIZ_EVENT_QUEUE;
    }

    //当前这个实体的粉丝集合的key
    public static String getFollowerKey(Long entityId, Integer entityType)
    {
        return BIZ_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }
    //当前用户关注了哪种类型实体 这种实体集合的key
    public static String getFolloweeKey(Long userId, Integer entityType)
    {
        return BIZ_FOLLOWEE + SPLIT + userId + SPLIT + entityType;
    }
}

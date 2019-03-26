package bekyiu.util;

//用于集中生成redis的key, 便于管理
public class RedisKeyGenerator
{
    public static final String SPLIT = ":";
    public static final String BIZ_LIKE = "LIKE";
    public static final String BIZ_DISLIKE = "DISLIKE";

    //得到对于当前想点赞的对象的key
    public static String getLikeKey(Long entityId, Integer entityType)
    {
        return BIZ_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDislikeKey(Long entityId, Integer entityType)
    {
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }
}

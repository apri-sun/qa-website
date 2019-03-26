package bekyiu.util;

//用于集中生成redis的key, 便于管理
public class RedisKeyGenerator
{
    private static final String SPLIT = ":";
    private static final String BIZ_LIKE = "LIKE";
    private static final String BIZ_DISLIKE = "DISLIKE";

    //得到对于当前想点赞的对象的key
    public static String getLikeKey(Integer entityId, Integer entityType)
    {
        return BIZ_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

    public static String getDislikeKey(Integer entityId, Integer entityType)
    {
        return BIZ_DISLIKE + SPLIT + entityType + SPLIT + entityId;
    }
}

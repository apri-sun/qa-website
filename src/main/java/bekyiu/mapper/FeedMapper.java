package bekyiu.mapper;

import bekyiu.domain.Feed;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FeedMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(Feed feed);

    Feed selectByPrimaryKey(Long id);

    List<Feed> selectAll();

    int updateByPrimaryKey(Feed feed);

    /**
     * pull模式 从我当前关注的人中, 增量的拉取出新鲜事
     * @param maxId 每次拉取出的新鲜事的id必须要小于maxId
     * @param userIds 关注的人的id
     * @param count 分页, 每次拉多少条
     * @return
     */
    List<Feed> selectUserFeeds(@Param("maxId") Long maxId,
                               @Param("userIds") List<Long> userIds,
                               @Param("count") Integer count);
}
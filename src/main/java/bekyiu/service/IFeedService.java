package bekyiu.service;

import bekyiu.domain.Feed;

import java.util.List;

public interface IFeedService
{
    void delete(Long id);

    void save(Feed feed);

    Feed get(Long id);

    List<Feed> listAll();

    void update(Feed feed);

    /**
     * pull模式 从我当前关注的人中, 增量的拉取出新鲜事
     * @param maxId 每次拉取出的新鲜事的id必须要小于maxId
     * @param userIds 关注的人的id
     * @param count 分页, 每次拉多少条
     * @return
     */
    List<Feed> selectUserFeeds(Long maxId,
                               List<Long> userIds,
                               Integer count);
}

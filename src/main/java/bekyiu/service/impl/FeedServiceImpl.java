package bekyiu.service.impl;

import bekyiu.domain.Feed;
import bekyiu.mapper.FeedMapper;
import bekyiu.service.IFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FeedServiceImpl implements IFeedService
{
    @Autowired
    private FeedMapper feedMapper;

    @Override
    public void delete(Long id)
    {
        feedMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Feed feed)
    {
        feedMapper.insert(feed);
    }

    @Override
    public Feed get(Long id)
    {
        return feedMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Feed> listAll()
    {
        return feedMapper.selectAll();
    }

    @Override
    public void update(Feed feed)
    {
        feedMapper.updateByPrimaryKey(feed);
    }

    @Override
    public List<Feed> selectUserFeeds(Long maxId, List<Long> userIds, Integer count)
    {
        return selectUserFeeds(maxId, userIds, count);
    }
}

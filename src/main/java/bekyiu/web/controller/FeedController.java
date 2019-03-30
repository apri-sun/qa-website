package bekyiu.web.controller;

import bekyiu.domain.Feed;
import bekyiu.domain.HostHolder;
import bekyiu.service.IFeedService;
import bekyiu.service.IFollowService;
import bekyiu.util.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController
{
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private IFeedService feedService;
    @Autowired
    private IFollowService followService;

    @RequestMapping("/pullFeeds")
    public String getPullFeeds(Model model)
    {
        //1是匿名用户
        long localUserId = hostHolder.getUser().getId() == null ? 1L : hostHolder.getUser().getId();
        List<Integer> followeesId = new ArrayList<>();
        if (localUserId != 1L)
        {
            //查询出我关注的所有人
            followeesId = followService.getFollowees(localUserId, EntityType.ENTITY_USER, 0L, Long.MAX_VALUE);
        }
        //没有登录的话就是查询所有
        List<Feed> feeds = feedService.selectUserFeeds(Long.MAX_VALUE, ints2Longs(followeesId), 10);
        model.addAttribute("feeds", feeds);
        return "feeds";
    }


    private List<Long> ints2Longs(List<Integer> nums)
    {
        List<Long> list = new ArrayList<>();
        for (Integer num : nums)
        {
            list.add(Long.valueOf(num));
        }
        return list;
    }
}

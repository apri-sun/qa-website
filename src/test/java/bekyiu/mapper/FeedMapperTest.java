package bekyiu.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedMapperTest
{
    @Autowired
    private FeedMapper mapper;
    @Test
    public void selectUserFeeds()
    {
        mapper.selectUserFeeds(10L, null, 10);
    }
}
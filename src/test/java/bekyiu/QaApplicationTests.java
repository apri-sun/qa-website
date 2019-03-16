package bekyiu;

import bekyiu.domain.User;
import bekyiu.mapper.UserMapper;
import bekyiu.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QaApplicationTests
{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService service;

    @Test
    public void save()
    {
        User user = new User();
        for (int i = 0; i < 20; i++)
        {
            user.setUsername("user" + i);
            user.setPassword("p" + i);
            user.setSalt("s" + i);
            user.setHeadUrl("url");
            service.save(user);
        }
    }

    @Test
    public void update()
    {
        User user = userMapper.selectByPrimaryKey(1L);
        System.out.println("user = " + user);

        user.setUsername("aaaaaaaaaaaa");
        user.setPassword("ddddddddd");
        userMapper.updateByPrimaryKey(user);
        System.out.println("user = " + user);

    }

    @Test
    public void get()
    {
        User user = userMapper.selectByUsername("user614");
        System.out.println("user = " + user);
    }

    @Test
    public void delete()
    {
        userMapper.deleteByPrimaryKey(1L);
    }

    @Test
    public void listAll()
    {
        List<User> users = userMapper.selectAll();
        System.out.println("users = " + users);
    }

}

package bekyiu.service.impl;

import bekyiu.domain.User;
import bekyiu.mapper.UserMapper;
import bekyiu.service.IUserService;
import bekyiu.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(User user)
    {
        userMapper.insert(user);
    }

    @Override
    public void update(User user)
    {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User selectByUsername(String username)
    {
        return userMapper.selectByUsername(username);
    }

    @Override
    public Map<String, String> register(String username, String password)
    {
        Map<String, String> map = new HashMap<>();
        //null, "", "    " 都会返回true
        if(StringUtils.isBlank(username))
        {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password))
        {
            map.put("msg", "密码不能为空");
            return map;
        }
        if(userMapper.selectByUsername(username) != null)
        {
            map.put("msg", "用户名已存在");
            return map;
        }
        User user = new User();
        user.setUsername(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(MD5.encode(password + user.getSalt()));
        user.setHeadUrl("url");
        userMapper.insert(user);
        return map;
    }

    @Override
    public void delete(Long id)
    {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User get(Long id)
    {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listAll()
    {
        return userMapper.selectAll();
    }
}

package bekyiu.service.impl;

import bekyiu.domain.User;
import bekyiu.mapper.UserMapper;
import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer save(User user)
    {
        return userMapper.insert(user);
    }

    @Override
    public Integer update(User user)
    {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public Integer delete(Long id)
    {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User get(Long id)
    {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> listAll()
    {
        return userMapper.selectAll();
    }
}

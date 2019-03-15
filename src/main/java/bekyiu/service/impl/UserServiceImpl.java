package bekyiu.service.impl;

import bekyiu.domain.User;
import bekyiu.mapper.UserMapper;
import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

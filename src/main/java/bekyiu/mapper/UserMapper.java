package bekyiu.mapper;

import bekyiu.domain.User;

import java.util.List;

public interface UserMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(User user);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User user);
}
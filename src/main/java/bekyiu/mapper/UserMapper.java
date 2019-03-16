package bekyiu.mapper;

import bekyiu.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper
{
    int deleteByPrimaryKey(Long id);

    int insert(User user);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User user);

    User selectByUsername(String username);
}
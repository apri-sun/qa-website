package bekyiu.service;

import bekyiu.domain.User;

import java.util.List;
import java.util.Map;

public interface IUserService
{
    void delete(Long id);

    void save(User user);

    User get(Long id);

    List<User> listAll();

    void update(User user);

    User selectByUsername(String username);

    Map<String, String> register(String username, String password);
}

package bekyiu.service;

import bekyiu.domain.User;

import java.util.List;

public interface IUserService
{
    void delete(Long id);

    void save(User user);

    User get(Long id);

    List<User> listAll();

    void update(User user);
}

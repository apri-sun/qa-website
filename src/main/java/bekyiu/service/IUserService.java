package bekyiu.service;

import bekyiu.domain.User;

import java.util.List;

public interface IUserService
{
    Integer delete(Long id);

    Integer save(User user);

    User get(Long id);

    List<User> listAll();

    Integer update(User user);
}

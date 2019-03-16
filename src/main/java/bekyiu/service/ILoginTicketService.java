package bekyiu.service;

import bekyiu.domain.LoginTicket;

import java.util.List;

public interface ILoginTicketService
{
    void delete(Long id);

    void save(LoginTicket ticket);

    LoginTicket get(Long id);

    List<LoginTicket> listAll();

    void update(LoginTicket ticket);

    LoginTicket selectByTicket(String ticket);

    void updateStatusByTicket(String ticket, Integer status);
}

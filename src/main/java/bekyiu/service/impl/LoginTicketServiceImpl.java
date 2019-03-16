package bekyiu.service.impl;

import bekyiu.domain.LoginTicket;
import bekyiu.mapper.LoginTicketMapper;
import bekyiu.service.ILoginTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class LoginTicketServiceImpl implements ILoginTicketService
{
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Override
    public void delete(Long id)
    {
        loginTicketMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(LoginTicket ticket)
    {
        loginTicketMapper.insert(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginTicket get(Long id)
    {
        return loginTicketMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoginTicket> listAll()
    {
        return loginTicketMapper.selectAll();
    }

    @Override
    public void update(LoginTicket ticket)
    {
        loginTicketMapper.updateByPrimaryKey(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginTicket selectByTicket(String ticket)
    {
        return loginTicketMapper.selectByTicket(ticket);
    }

    @Override
    public void updateStatusByTicket(String ticket, Integer status)
    {
        loginTicketMapper.updateStatusByTicket(ticket, status);
    }
}

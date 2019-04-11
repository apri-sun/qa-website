package bekyiu.service;

import bekyiu.domain.LoginTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ILoginTicketServiceTest
{

    @Autowired
    private ILoginTicketService service;
    @Test
    public void delete()
    {
        service.delete(1l);
    }

    @Test
    public void save()
    {
        LoginTicket ticket = new LoginTicket();
        ticket.setExpired(new Date());
        service.save(ticket);
    }

    @Test
    public void get()
    {
        System.out.println(service.get(1L));
    }

    @Test
    public void listAll()
    {
        System.out.println(service.listAll());
    }

    @Test
    public void update()
    {
        LoginTicket ticket = service.get(1L);
        ticket.setTicket("sb");
        service.update(ticket);
    }

    @Test
    public void selectByTicket()
    {
        System.out.println(service.selectByTicket("sb3"));
    }

    @Test
    public void updateStatusByTicket()
    {
        service.updateStatusByTicket("sb", 1);
    }
}
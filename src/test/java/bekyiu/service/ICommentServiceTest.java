package bekyiu.service;

import bekyiu.domain.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ICommentServiceTest
{

    @Autowired
    private ICommentService service;

    @Test
    public void update()
    {
        Comment comment = service.get(1L);
        comment.setContent("sssssssswwwwwwwwwwwwww99");
        service.update(comment);
    }

    @Test
    public void delete()
    {
        service.delete(1L);
        service.delete(2L);
    }

    @Test
    public void save()
    {
        Comment c = new Comment();
        c.setContent("saaaaaaasssss");
        c.setCreatedDate(new Date());
        service.save(c);
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
    public void selectCommentByEntity()
    {
    }

    @Test
    public void getCommentCount()
    {
    }
}
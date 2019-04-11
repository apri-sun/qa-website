package bekyiu.service;

import bekyiu.domain.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IQuestionServiceTest
{

    @Autowired
    private IQuestionService service;

    @Test
    public void delete()
    {
        service.delete(1L);
    }

    @Test
    public void save()
    {
        Question question = new Question();
        for (int i = 0; i < 20; i++)
        {
            question.setTitle("TITLE" + i);
            question.setUserId((long) (i + 1));
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * i);
            question.setCreateDate(date);
            question.setContent(i + "balalalalalalalalalalalalalalalabalalalalalalalalalalalalalalala" +
                    "balalalalalalalalalalalalalalalabalalalalalalalalalalalalalalala");
            question.setCommentCount(i + 1);
            service.save(question);
        }
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
        Question question = service.get(2L);
        question.setContent("0lalababa");
        question.setCreateDate(new Date());
        service.update(question);
    }
}
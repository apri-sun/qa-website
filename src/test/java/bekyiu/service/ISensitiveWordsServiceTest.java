package bekyiu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ISensitiveWordsServiceTest
{

    @Autowired
    private ISensitiveWordsService service;
    @Test
    public void filter()
    {
//        String s0 = service.filter("你好色情啊");
//        System.out.println("s = " + s0);
        String s = service.filter("吸毒吗?你好色123情啊操你妈嘻嘻嘻");
        System.out.println("s = " + s);
        String s1 = service.filter("色情");
        System.out.println("s = " + s1);
        String s2 = service.filter("色情啊");
        System.out.println("s = " + s2);
        String s3 = service.filter("你色情");
        System.out.println("s = " + s3);
        String a = service.filter("我是一句正常的话");
        System.out.println("a = " + a);
    }
}
package bekyiu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("bekyiu.mapper")
@EnableTransactionManagement
public class QaApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(QaApplication.class, args);
    }

}

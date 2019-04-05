package bekyiu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = SolrAutoConfiguration.class)
@MapperScan("bekyiu.mapper")
@EnableTransactionManagement
public class QaApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(QaApplication.class, args);
    }

}

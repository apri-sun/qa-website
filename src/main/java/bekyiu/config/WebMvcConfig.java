package bekyiu.config;

import bekyiu.web.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    PassportInterceptor passportInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        //springboot 默认只会找/static一级目录, static下的文件夹就不认识了
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(passportInterceptor).addPathPatterns("/**").excludePathPatterns("/loginToHtml", "/reg", "/login");
    }
}

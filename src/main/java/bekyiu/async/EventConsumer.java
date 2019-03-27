package bekyiu.async;

import bekyiu.util.JedisAdapter;
import bekyiu.util.RedisKeyGenerator;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
//从阻塞队列中取出事件并执行
public class EventConsumer implements InitializingBean, ApplicationContextAware
{
    //当前被取出的事件, 需要触发哪些handler
    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    private ApplicationContext ctx;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    //在EventHandler中已经知道了handler到eventType的映射
    //现在要根据这个映射关系, 反过来求出每个eventType到某些handler的映射, 即this.config
    public void afterPropertiesSet() throws Exception
    {
        //从上下文中找出所有的handler
        Map<String, EventHandler> beans = ctx.getBeansOfType(EventHandler.class);
        if(beans != null)
        {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet())
            {
                //拿到每个handler
                EventHandler curHandler = entry.getValue();
                //拿到每个handler要处理的EventType
                List<EventType> supportEventTypes = curHandler.getSupportEventTypes();
                //添加映射关系
                for (EventType type : supportEventTypes)
                {
                    if(!config.containsKey(type))
                    {
                        config.put(type, new ArrayList<>());
                    }
                    config.get(type).add(curHandler);
                }
            }
        }

        //取事件并执行
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(true)
                {
                    String queueKey = RedisKeyGenerator.getEventQueueKey();
                    List<String> brpop = jedisAdapter.brpop(0, queueKey);
                    for (String eventModel : brpop)
                    {
                        if(queueKey.equals(eventModel))
                        {
                            //因为第一个值是key
                            continue;
                        }
                        //当前要处理的现场
                        EventModel model = JSON.parseObject(eventModel, EventModel.class);
                        if(!config.containsKey(model.getType()))
                        {
                            System.out.println("不能识别的事件");
                            continue;
                        }
                        //处理该现场的handlers
                        List<EventHandler> eventHandlers = config.get(model.getType());
                        for (EventHandler handler : eventHandlers)
                        {
                            handler.doHandle(model);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.ctx = applicationContext;
    }
}

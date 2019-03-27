package bekyiu.async;

import bekyiu.util.JedisAdapter;
import bekyiu.util.RedisKeyGenerator;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//把EventModel往阻塞队列里推
@Component
public class EventProducer
{
    @Autowired
    private JedisAdapter jedisAdapter;

    public Boolean fireEvent(EventModel eventModel)
    {
        try
        {
            String queueKey = RedisKeyGenerator.getEventQueueKey();
            String model = JSONObject.toJSONString(eventModel);
            //阻塞队列用redis实现
            jedisAdapter.lpush(queueKey, model);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

package bekyiu.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

//默认单例
@Component
public class JedisAdapter implements InitializingBean
{
    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        pool = new JedisPool("redis://localhost:6379/1");
    }

    public Long sadd(String key, String... value)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }
    public Long scard(String key)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.scard(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }
    public Boolean sismember(String key, String value)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }
    public Long srem(String key, String... value)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.srem(key, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }

    //list
    public Long lpush(String key, String... value)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }

    //第一个返回值是key, 第二个是被弹出的value
    public List<String> brpop(Integer timeout, String... key)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (jedis != null)
            {
                jedis.close();
            }
        }
        return null;
    }
}

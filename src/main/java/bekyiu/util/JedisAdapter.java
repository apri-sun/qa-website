package bekyiu.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    //set
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
        return new ArrayList<>();
    }

    //zset
    public Long zadd(String key, Double score, String value)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.zadd(key, score, value);
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
    public Long zrem(String key, String... value)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.zrem(key, value);
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
    public Long zcard(String key)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.zcard(key);
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
    public Set<String> zrevrange(String key, Long start, Long stop)
    {
        Jedis jedis = null;
        try
        {
            jedis = pool.getResource();
            return jedis.zrevrange(key, start, stop);
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

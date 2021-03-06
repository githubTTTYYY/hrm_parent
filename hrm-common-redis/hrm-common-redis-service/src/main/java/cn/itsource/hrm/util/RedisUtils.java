package cn.itsource.hrm.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description TODO
 * @Author 初代火影
 * @Date 2019/12/27 18:12
 * @Version v1.0
 */
@Component
@Scope("singleton")
public class RedisUtils {
    private String host="127.0.0.1";
    private Integer prot=6379;
    private Integer maxIdle=1;
    private Integer maxTotal=11;
    private Long maxWaitMillis=10 * 1000L;
    private boolean testOnBorrow=true;
    JedisPool jedisPool = null;

    public RedisUtils() {
        //1 创建连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        //2 进行配置-四个配置
        config.setMaxIdle(maxIdle);//最小连接数
        config.setMaxTotal(maxTotal);//最大连接数
        config.setMaxWaitMillis(maxWaitMillis);//最长等待时间
        config.setTestOnBorrow(testOnBorrow);//测试连接时是否畅通
        //3 通过配置对象创建连接池对象
        jedisPool = new JedisPool(config, host, prot, maxWaitMillis.intValue());
    }

    //获取连接
    public Jedis getSource() {
        return jedisPool.getResource();
    }

    //关闭资源
    public void closeSource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }

    }

    /**
     * 设置字符值
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = getSource();
        jedis.set(key, value);
        closeSource(jedis);
    }

    /**
     * 设置
     * @param key
     * @param value
     */
    public void set(byte[] key, byte[] value) {
        Jedis jedis = getSource();
        jedis.set(key, value);
        closeSource(jedis);
    }

    /**
     *
     * @param key
     * @return
     */
    public byte[]  get(byte[] key) {
        Jedis jedis = getSource();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSource(jedis);
        }
        return null;

    }

    /**
     * 设置字符值
     *
     * @param key
     */
    public String get(String key) {
        Jedis jedis = getSource();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSource(jedis);
        }

        return null;

    }

}

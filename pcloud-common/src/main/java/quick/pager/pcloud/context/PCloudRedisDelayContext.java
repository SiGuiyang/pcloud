package quick.pager.pcloud.context;

import com.alibaba.fastjson.JSON;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import quick.pager.pcloud.handler.RedisDelayHandler;
import quick.pager.pcloud.model.RedisDelayDTO;

@Slf4j
public class PCloudRedisDelayContext {

    private RedissonClient redissonClient;

    public PCloudRedisDelayContext(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * redis 延迟队列code
     */
    private static final String REDIS_DELAY_CODE = "redisDelayCode";

    /**
     * 添加延迟队列
     *
     * @param value     存入延迟队列的数据
     * @param delayTime 加入延迟队列的时间
     * @param timeUnit  时间单位
     */
    public boolean setRedisDelay(final String value, final long delayTime, final TimeUnit timeUnit) {
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(REDIS_DELAY_CODE, new StringCodec());
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        delayedQueue.offer(value, delayTime, timeUnit);
        return true;
    }

    /**
     * 添加延迟队列
     */
    public void doRedisDelay() {
        RBlockingDeque<String> blockingDeque = redissonClient.getBlockingDeque(REDIS_DELAY_CODE, new StringCodec());

        try {
            String result = blockingDeque.take();

            log.info("延迟队列接收的数据 {}", result);
            RedisDelayDTO redisDelayDTO = JSON.parseObject(result, RedisDelayDTO.class);

            RedisDelayHandler redisDelayHandler = PCloudContext.getBean(redisDelayDTO.getBeanName(), RedisDelayHandler.class);

            redisDelayHandler.doExecute(redisDelayDTO.getBeanName());

            log.info("延迟队列执行结束");

        } catch (Exception e) {
            log.error("延迟队列数据异常 {}", e);
        }
    }

}

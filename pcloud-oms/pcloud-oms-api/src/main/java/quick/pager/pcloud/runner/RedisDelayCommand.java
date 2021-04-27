package quick.pager.pcloud.runner;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import quick.pager.pcloud.context.PCloudRedisDelayContext;

/**
 * 守护线程监听redis延迟队列数据
 *
 * @author siguiyang
 */
@Component
@Slf4j
public class RedisDelayCommand implements CommandLineRunner {

    @Resource
    private PCloudRedisDelayContext pCloudRedisDelayContext;

    @Override
    public void run(String... args) throws Exception {

        Thread thread = new Thread(() -> {

            StopWatch watch = new StopWatch();
            while (true) {
                watch.start("oms延迟队列");
                pCloudRedisDelayContext.doRedisDelay();
                watch.stop();
                log.info("延迟队列执行周期 {}", watch.prettyPrint());
            }
        });

        thread.setName("oms-delay-thread");
        thread.setDaemon(true);
        thread.start();
    }
}

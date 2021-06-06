package quick.pager.pcloud.configuration;

import com.alibaba.fastjson.JSON;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.SystemHealth;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HealthRunner implements CommandLineRunner {

    @Resource
    private HealthEndpoint healthEndpoint;

    private static final ScheduledExecutorService POOL = Executors.newScheduledThreadPool(1);


    @Override
    public void run(String... args) throws Exception {

        POOL.scheduleWithFixedDelay(() -> {

            HealthComponent health = healthEndpoint.health();

            if (health instanceof SystemHealth) {
                SystemHealth systemHealth = (SystemHealth) health;

                Map<String, HealthComponent> components = systemHealth.getComponents();
                log.info("健康心跳结果 result = {}", JSON.toJSONString(components));
            }

        }, 1, 30, TimeUnit.SECONDS);
    }
}

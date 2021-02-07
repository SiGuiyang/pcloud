package quick.pager.pcloud.configuration;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class PCloudConfiguration {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor poolExecutor = new ThreadPoolTaskExecutor();
        poolExecutor.setCorePoolSize(50);
        poolExecutor.setMaxPoolSize(100);
        poolExecutor.setQueueCapacity(2000);
        poolExecutor.setKeepAliveSeconds(500);
        poolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        poolExecutor.setAllowCoreThreadTimeOut(true);
        poolExecutor.setWaitForTasksToCompleteOnShutdown(true);
        poolExecutor.setThreadNamePrefix("pcloud");
        return poolExecutor;
    }
}

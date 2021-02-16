package quick.pager.pcloud.service.impl;

import java.math.BigDecimal;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import quick.pager.pcloud.service.PCloudService;

@Service
@Slf4j
public class PCloudServiceImpl implements PCloudService {

    @Resource
    private RedissonClient redissonClient;

    private static final String REDIS_PREFIX = "redis:";

    private static final String REDIS_LOCK_PREFIX = "redis:lock:";


    @Override
    public BigDecimal getMifen(String key, BigDecimal optValue, String opt) {

        RLock lock = redissonClient.getLock(REDIS_LOCK_PREFIX.concat(key));
        lock.lock();
        boolean updateOk = false;
        try {
            RBucket<BigDecimal> bucket = redissonClient.getBucket(REDIS_PREFIX.concat(key));

            BigDecimal bigDecimal = bucket.get();

            BigDecimal decimal = Objects.nonNull(bigDecimal) ? bigDecimal.add(optValue) : optValue;

            bucket.set(decimal);
            updateOk = true;

            log.info("缓冲中的值 updateOk = {}, bigDecimal = {}", updateOk, decimal);
            return bigDecimal;
        } finally {
            if (updateOk && lock.isLocked()) {
                lock.unlock();
            }
        }

    }
}

package quick.pager.pcloud.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.util.ShardingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestXxlJob {


    @XxlJob("testXxlJob")
    public ReturnT<String> process(String params) {

        log.info("开始调用xxl-job定时任务");

        ShardingUtil.ShardingVO shardingVo = ShardingUtil.getShardingVo();
        int index = shardingVo.getIndex();
        int total = shardingVo.getTotal();

        for (int i = 0; i < total; i++) {
            if (index == i) {
                log.info("分片开始");
            }
        }

        return ReturnT.SUCCESS;
    }
}

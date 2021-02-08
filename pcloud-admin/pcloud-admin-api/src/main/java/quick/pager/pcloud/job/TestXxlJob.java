package quick.pager.pcloud.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestXxlJob {


    @XxlJob("testXxlJob")
    public ReturnT<String> process(String params) {

        log.info("开始调用xxl-job定时任务");

        return ReturnT.SUCCESS;
    }
}

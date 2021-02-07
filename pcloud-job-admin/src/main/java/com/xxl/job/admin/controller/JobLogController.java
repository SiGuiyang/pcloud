package com.xxl.job.admin.controller;

import com.xxl.job.admin.common.JobLogParams;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.core.scheduler.XxlJobScheduler;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.dao.XxlJobInfoDao;
import com.xxl.job.admin.dao.XxlJobLogDao;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.model.KillParam;
import com.xxl.job.core.biz.model.LogParam;
import com.xxl.job.core.biz.model.LogResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

/**
 * index controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@RestController
@RequestMapping("/joblog")
public class JobLogController {
    private static Logger logger = LoggerFactory.getLogger(JobLogController.class);

    @Resource
    private XxlJobGroupDao xxlJobGroupDao;
    @Resource
    public XxlJobInfoDao xxlJobInfoDao;
    @Resource
    public XxlJobLogDao xxlJobLogDao;

    @RequestMapping
    public ReturnT<Map<String, Object>> index(@RequestParam(required = false, defaultValue = "0") Integer jobId) {

        Map<String, Object> data = new HashMap<>();
        // 执行器列表
        List<XxlJobGroup> jobGroupList_all = xxlJobGroupDao.findAll();

        data.put("JobGroupList", jobGroupList_all);

        // 任务
        if (jobId > 0) {
            XxlJobInfo jobInfo = xxlJobInfoDao.loadById(jobId);
            if (jobInfo == null) {
                throw new RuntimeException(I18nUtil.getString("jobinfo_field_id") + I18nUtil.getString("system_unvalid"));
            }

            data.put("jobInfo", jobInfo);
        }

        return new ReturnT<>(data);
    }

    @RequestMapping("/getJobsByGroup")
    public Map<String, Object> getJobsByGroup(int jobGroup) {
        // package result
        Map<String, Object> maps = new HashMap<>();
        maps.put("code", 200);
        maps.put("data", xxlJobInfoDao.getJobsByGroup(jobGroup));

        return maps;
    }

    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestBody JobLogParams params) {
        // parse param
        Date triggerTimeStart = null;
        Date triggerTimeEnd = null;
        if (!CollectionUtils.isEmpty(params.getFilterTime())) {
            triggerTimeStart = params.getFilterTime().get(0);
            triggerTimeEnd = params.getFilterTime().get(1);
        }

        // page query
        List<XxlJobLog> list = xxlJobLogDao.pageList((params.getPage() - 1) * params.getPageSize(), params.getPageSize(), params.getJobGroup(), params.getJobId(), triggerTimeStart, triggerTimeEnd, params.getLogStatus());
        int list_count = xxlJobLogDao.pageListCount(params.getJobGroup(), params.getJobId(), triggerTimeStart, triggerTimeEnd, params.getLogStatus());

        // package result
        Map<String, Object> maps = new HashMap<>();
        maps.put("code", 200);
        maps.put("total", list_count);        // 总记录数
        maps.put("data", list);                    // 分页列表
        return maps;
    }

    @RequestMapping("/logDetailPage")
    public Map<String, Object> logDetailPage(int id) {

        // base check
        XxlJobLog jobLog = xxlJobLogDao.load(id);
        if (jobLog == null) {
            throw new RuntimeException(I18nUtil.getString("joblog_logid_unvalid"));
        }

        Map<String, Object> data = new HashMap<>();

        data.put("triggerCode", jobLog.getTriggerCode());
        data.put("handleCode", jobLog.getHandleCode());
        data.put("executorAddress", jobLog.getExecutorAddress());
        data.put("triggerTime", jobLog.getTriggerTime().getTime());
        data.put("logId", jobLog.getId());
        return data;
    }

    @RequestMapping("/logDetailCat")
    public ReturnT<LogResult> logDetailCat(String executorAddress, long triggerTime, long logId, int fromLineNum) {
        try {
            ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(executorAddress);
            ReturnT<LogResult> logResult = executorBiz.log(new LogParam(triggerTime, logId, fromLineNum));

            // is end
            if (logResult.getContent() != null && logResult.getContent().getFromLineNum() > logResult.getContent().getToLineNum()) {
                XxlJobLog jobLog = xxlJobLogDao.load(logId);
                if (jobLog.getHandleCode() > 0) {
                    logResult.getContent().setEnd(true);
                }
            }

            return logResult;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ReturnT<>(ReturnT.FAIL_CODE, e.getMessage());
        }
    }

    @RequestMapping("/logKill")
    public ReturnT<String> logKill(int id) {
        // base check
        XxlJobLog log = xxlJobLogDao.load(id);
        XxlJobInfo jobInfo = xxlJobInfoDao.loadById(log.getJobId());
        if (jobInfo == null) {
            return new ReturnT<>(500, I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
        }
        if (ReturnT.SUCCESS_CODE != log.getTriggerCode()) {
            return new ReturnT<>(500, I18nUtil.getString("joblog_kill_log_limit"));
        }

        // request of kill
        ReturnT<String> runResult;
        try {
            ExecutorBiz executorBiz = XxlJobScheduler.getExecutorBiz(log.getExecutorAddress());
            runResult = executorBiz.kill(new KillParam(jobInfo.getId()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            runResult = new ReturnT<>(500, e.getMessage());
        }

        if (ReturnT.SUCCESS_CODE == runResult.getCode()) {
            log.setHandleCode(ReturnT.FAIL_CODE);
            log.setHandleMsg(I18nUtil.getString("joblog_kill_log_byman") + ":" + (runResult.getMsg() != null ? runResult.getMsg() : ""));
            log.setHandleTime(new Date());
            xxlJobLogDao.updateHandleInfo(log);
            return new ReturnT<>(runResult.getMsg());
        } else {
            return new ReturnT<>(500, runResult.getMsg());
        }
    }

    @RequestMapping("/clearLog")
    public ReturnT<String> clearLog(@RequestBody JobLogParams params) {

        int type = params.getType();
        int jobGroup = params.getJobGroup();
        int jobId = params.getJobId();

        Date clearBeforeTime = null;
        int clearBeforeNum = 0;
        if (type == 1) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -1);    // 清理一个月之前日志数据
        } else if (type == 2) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -3);    // 清理三个月之前日志数据
        } else if (type == 3) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -6);    // 清理六个月之前日志数据
        } else if (type == 4) {
            clearBeforeTime = DateUtil.addYears(new Date(), -1);    // 清理一年之前日志数据
        } else if (type == 5) {
            clearBeforeNum = 1000;        // 清理一千条以前日志数据
        } else if (type == 6) {
            clearBeforeNum = 10000;        // 清理一万条以前日志数据
        } else if (type == 7) {
            clearBeforeNum = 30000;        // 清理三万条以前日志数据
        } else if (type == 8) {
            clearBeforeNum = 100000;    // 清理十万条以前日志数据
        } else if (type == 9) {
            clearBeforeNum = 0;            // 清理所有日志数据
        } else {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("joblog_clean_type_unvalid"));
        }

        List<Long> logIds;
        do {
            logIds = xxlJobLogDao.findClearLogIds(jobGroup, jobId, clearBeforeTime, clearBeforeNum, 1000);
            if (logIds != null && logIds.size() > 0) {
                xxlJobLogDao.clearLog(logIds);
            }
        } while (logIds != null && logIds.size() > 0);

        return ReturnT.SUCCESS;
    }

}

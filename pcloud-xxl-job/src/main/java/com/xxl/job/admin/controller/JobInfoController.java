package com.xxl.job.admin.controller;

import com.xxl.job.admin.common.JobInfoParams;
import com.xxl.job.admin.common.TriggerJobParam;
import com.xxl.job.admin.core.cron.CronExpression;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.xxl.job.admin.core.thread.JobTriggerPoolHelper;
import com.xxl.job.admin.core.trigger.TriggerTypeEnum;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.XxlJobGroupDao;
import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.glue.GlueTypeEnum;
import com.xxl.job.core.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.ParseException;
import org.springframework.web.bind.annotation.RestController;

/**
 * index controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@RestController
@RequestMapping("/jobinfo")
public class JobInfoController {

    @Resource
    private XxlJobGroupDao xxlJobGroupDao;
    @Resource
    private XxlJobService xxlJobService;

    @RequestMapping
    public ReturnT<Map<String, Object>> index(@RequestParam(required = false, defaultValue = "-1") int jobGroup) {

        Map<String, Object> data = new HashMap<>();
        // 枚举-字典
        data.put("ExecutorRouteStrategyEnum", ExecutorRouteStrategyEnum.values());        // 路由策略-列表
        data.put("GlueTypeEnum", GlueTypeEnum.values());                                // Glue类型-字典
        data.put("ExecutorBlockStrategyEnum", ExecutorBlockStrategyEnum.values());        // 阻塞处理策略-字典

        // 执行器列表
        List<XxlJobGroup> jobGroupList_all = xxlJobGroupDao.findAll();

        data.put("JobGroupList", jobGroupList_all);
        data.put("jobGroup", jobGroup);

        return new ReturnT<>(data);
    }

    @RequestMapping("/pageList")
    public Map<String, Object> pageList(@RequestBody JobInfoParams params) {

        return xxlJobService.pageList(params.getPage(), params.getPageSize(), params.getJobGroup(), params.getTriggerStatus(), params.getJobDesc(), params.getExecutorHandler(), params.getAuthor());
    }

    @RequestMapping("/add")
    public ReturnT<String> add(@RequestBody XxlJobInfo jobInfo) {
        return xxlJobService.add(jobInfo);
    }

    @RequestMapping("/update")
    public ReturnT<String> update(@RequestBody XxlJobInfo jobInfo) {
        return xxlJobService.update(jobInfo);
    }

    @RequestMapping("/{id}/remove")
    public ReturnT<String> remove(@PathVariable("id") int id) {
        return xxlJobService.remove(id);
    }

    @RequestMapping("/{id}/stop")
    public ReturnT<String> pause(@PathVariable("id") int id) {
        return xxlJobService.stop(id);
    }

    @RequestMapping("/{id}/start")
    public ReturnT<String> start(@PathVariable("id") int id) {
        return xxlJobService.start(id);
    }

    @RequestMapping("/trigger")
    public ReturnT<String> triggerJob(@RequestBody TriggerJobParam param) {
        String executorParam = param.getExecutorParam();
        String addressList = param.getAddressList();
        int id = param.getId();

        // force cover job param
        if (executorParam == null) {
            executorParam = "";
        }

        JobTriggerPoolHelper.trigger(id, TriggerTypeEnum.MANUAL, -1, null, executorParam, addressList);
        return ReturnT.SUCCESS;
    }

    @RequestMapping("/nextTriggerTime")
    public ReturnT<List<String>> nextTriggerTime(@RequestParam("cron") String cron) {
        List<String> result = new ArrayList<>();
        try {
            CronExpression cronExpression = new CronExpression(cron);
            Date lastTime = new Date();
            for (int i = 0; i < 5; i++) {
                lastTime = cronExpression.getNextValidTimeAfter(lastTime);
                if (lastTime != null) {
                    result.add(DateUtil.formatDateTime(lastTime));
                } else {
                    break;
                }
            }
        } catch (ParseException e) {
            return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("jobinfo_field_cron_unvalid"));
        }
        return new ReturnT<>(result);
    }

}

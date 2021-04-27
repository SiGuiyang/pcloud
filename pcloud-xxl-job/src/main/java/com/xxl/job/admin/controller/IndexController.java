package com.xxl.job.admin.controller;

import com.xxl.job.admin.service.XxlJobService;
import com.xxl.job.core.biz.model.ReturnT;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;

/**
 * index controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@RestController
public class IndexController {

    @Resource
    private XxlJobService xxlJobService;


    @RequestMapping("/statistics")
    public Map<String, Object> statistics() {
        Map<String, Object> dashboardMap = new HashMap<>();
        dashboardMap.put("code", 200);
        dashboardMap.put("data", xxlJobService.dashboardInfo());

        return dashboardMap;
    }

    @RequestMapping("/chartInfo")
    @ResponseBody
    public ReturnT<Map<String, Object>> chartInfo() {
        LocalDateTime startTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);
        return xxlJobService.chartInfo(Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant()), Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant()));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

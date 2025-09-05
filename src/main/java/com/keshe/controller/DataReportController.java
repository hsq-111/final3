package com.keshe.controller;

import com.keshe.pojo.Result;
import com.keshe.server.DataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataReportController {

    @Autowired
    private DataReportService dataReportService;

    /**
     * 获取系统概览数据
     */
    @GetMapping("/overview")
    public Result<Map<String, Object>> getSystemOverview() {
        Map<String, Object> overview = dataReportService.getSystemOverview();
        return Result.success(overview);
    }

    /**
     * 获取热门课程统计
     */
    @GetMapping("/popular-classes")
    public Result<Map<String, Object>> getPopularClasses(
            @RequestParam(defaultValue = "7") Integer days,
            @RequestParam(defaultValue = "10") Integer limit) {
        // 计算开始日期
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);
        
        Map<String, Object> popularClasses = dataReportService.getPopularClasses(startDate, endDate, limit);
        return Result.success(popularClasses);
    }

    /**
     * 获取用户活跃度统计
     */
    @GetMapping("/user-activity")
    public Result<Map<String, Object>> getUserActivity(
            @RequestParam(defaultValue = "30") Integer days) {
        // 计算开始日期
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days);
        
        Map<String, Object> userActivity = dataReportService.getUserActivity(startDate, endDate);
        return Result.success(userActivity);
    }
}
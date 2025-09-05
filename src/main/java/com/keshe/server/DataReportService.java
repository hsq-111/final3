package com.keshe.server;

import java.time.LocalDate;
import java.util.Map;

public interface DataReportService {
    /**
     * 获取系统概览数据
     */
    Map<String, Object> getSystemOverview();

    /**
     * 获取热门课程统计
     */
    Map<String, Object> getPopularClasses(LocalDate startDate, LocalDate endDate, Integer limit);

    /**
     * 获取用户活跃度统计
     */
    Map<String, Object> getUserActivity(LocalDate startDate, LocalDate endDate);
}
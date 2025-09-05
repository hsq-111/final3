package com.keshe.server.Impl;

import com.keshe.mapper.DataReportMapper;
import com.keshe.server.DataReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataReportServiceImpl implements DataReportService {

    @Autowired
    private DataReportMapper dataReportMapper;

    @Override
    public Map<String, Object> getSystemOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        // 获取总用户数
        Integer totalUsers = dataReportMapper.selectTotalUsers();
        
        // 获取今日新增用户数
        Integer newUsersToday = dataReportMapper.selectNewUsersToday();
        
        // 获取总课程数
        Integer totalClasses = dataReportMapper.selectTotalClasses();
        
        // 获取今日课程数
        Integer classesToday = dataReportMapper.selectClassesToday();
        
        // 获取总预约数
        Integer totalReservations = dataReportMapper.selectTotalReservations();
        
        // 获取今日预约数
        Integer reservationsToday = dataReportMapper.selectReservationsToday();
        
        overview.put("totalUsers", totalUsers);
        overview.put("newUsersToday", newUsersToday);
        overview.put("totalClasses", totalClasses);
        overview.put("classesToday", classesToday);
        overview.put("totalReservations", totalReservations);
        overview.put("reservationsToday", reservationsToday);
        
        return overview;
    }

    @Override
    public Map<String, Object> getPopularClasses(LocalDate startDate, LocalDate endDate, Integer limit) {
        Map<String, Object> result = new HashMap<>();
        
        // 获取热门课程列表
        List<Map<String, Object>> popularClasses = dataReportMapper.selectPopularClasses(startDate, endDate, limit);
        
        // 获取课程类型分布
        List<Map<String, Object>> classTypeDistribution = dataReportMapper.selectClassTypeDistribution(startDate, endDate);
        
        result.put("popularClasses", popularClasses);
        result.put("classTypeDistribution", classTypeDistribution);
        
        return result;
    }

    @Override
    public Map<String, Object> getUserActivity(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> result = new HashMap<>();
        // 获取用户活跃度趋势
        List<Map<String, Object>> activityTrend = dataReportMapper.selectUserActivityTrend(startDate, endDate);
        if (activityTrend != null) {
            for (Map<String, Object> map : activityTrend) {
                map.entrySet().removeIf(e -> e.getKey() == null);
            }
        }
        // 获取活跃用户统计
        Map<String, Object> activeUserStats = dataReportMapper.selectActiveUserStats(startDate, endDate);
        if (activeUserStats != null) {
            activeUserStats.entrySet().removeIf(e -> e.getKey() == null);
        }
        // 获取用户预约偏好
        List<Map<String, Object>> userReservationPreferences = dataReportMapper.selectUserReservationPreferences(startDate, endDate);
        if (userReservationPreferences != null) {
            for (Map<String, Object> map : userReservationPreferences) {
                map.entrySet().removeIf(e -> e.getKey() == null);
            }
        }
        result.put("activityTrend", activityTrend);
        result.put("activeUserStats", activeUserStats);
        result.put("userReservationPreferences", userReservationPreferences);
        return result;
    }
}
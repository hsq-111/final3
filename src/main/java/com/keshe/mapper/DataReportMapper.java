package com.keshe.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface DataReportMapper {
    /**
     * 查询总用户数
     */
    Integer selectTotalUsers();

    /**
     * 查询今日新增用户数
     */
    Integer selectNewUsersToday();

    /**
     * 查询总课程数
     */
    Integer selectTotalClasses();

    /**
     * 查询今日课程数
     */
    Integer selectClassesToday();

    /**
     * 查询总预约数
     */
    Integer selectTotalReservations();

    /**
     * 查询今日预约数
     */
    Integer selectReservationsToday();

    /**
     * 查询热门课程
     */
    @MapKey("classId") // 假设数据库返回结果中有classId字段作为键
    List<Map<String, Object>> selectPopularClasses(LocalDate startDate, LocalDate endDate, Integer limit);

    /**
     * 查询课程类型分布
     */
    @MapKey("type") // 假设数据库返回结果中有"type"字段作为键
    List<Map<String, Object>> selectClassTypeDistribution(LocalDate startDate, LocalDate endDate);

    /**
     * 查询用户活跃度趋势
     */
     @MapKey("statType") // 根据实际返回的Map中的key来设置
    List<Map<String, Object>> selectUserActivityTrend(LocalDate startDate, LocalDate endDate);

    /**
     * 查询活跃用户统计
     */
    @MapKey("statType") // 假设返回的Map中包含 statType 字段作为键
    Map<String, Object> selectActiveUserStats(LocalDate startDate, LocalDate endDate);

    /**
     * 查询用户预约偏好
     */
    @MapKey("preferenceType") // 假设查询结果中包含 preferenceType 字段作为键
    List<Map<String, Object>> selectUserReservationPreferences(LocalDate startDate, LocalDate endDate);
}

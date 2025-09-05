package com.keshe.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecommendationMapper {
    /**
     * 基于用户偏好查询推荐课程
     */
    @MapKey("classId") // 假设返回结果中包含classId字段作为键
    List<Map<String, Object>> selectByUserPreferences(Integer userId, Integer start, Integer pageSize, String category);

    /**
     * 查询热门课程
     */
    @MapKey("classId") // 假设返回结果中包含classId字段作为键
    List<Map<String, Object>> selectPopularClasses(Integer start, Integer pageSize, String category);

    /**
     * 根据ID查询课程
     */
    @MapKey("classId") // 使用classId作为键
    Map<String, Object> selectClassById(Integer classId);

    /**
     * 根据用户ID和课程ID查询反馈
     */
    @MapKey("feedback_id")
    Map<String, Object> selectFeedbackByUserAndClass(Integer userId, Integer classId);

    /**
     * 插入反馈记录
     */
    void insertFeedback(Map<String, Object> feedback);

    /**
     * 更新反馈记录
     */
    void updateFeedback(Map<String, Object> params);

    /**
     * 查询推荐统计
     */
    @MapKey("statType") // 假设统计结果包含statType字段作为键
    Map<String, Object> selectRecommendationStats(Integer userId);

    /**
     * 查询用户兴趣分布
     */
    @MapKey("interestType") // 假设兴趣分布包含interestType字段作为键
    List<Map<String, Object>> selectInterestDistribution(Integer userId);

    /**
     * 查询最近的反馈
     */
    @MapKey("feedbackId") // 假设反馈记录有feedbackId字段作为键
    List<Map<String, Object>> selectRecentFeedback(Integer userId, Integer limit);
}

package com.keshe.server;

import java.util.List;
import java.util.Map;

public interface RecommendationService {
    /**
     * 获取推荐课程
     */
    List<Map<String, Object>> getRecommendedClasses(Integer userId, Integer page, Integer pageSize, String category);

    /**
     * 提交推荐反馈
     */
    void submitFeedback(Integer userId, Integer classId, String feedback) throws Exception;

    /**
     * 获取推荐分析
     */
    Map<String, Object> getRecommendationAnalysis(Integer userId);
}
package com.keshe.server.Impl;

import com.keshe.mapper.RecommendationMapper;
import com.keshe.server.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecommendationMapper recommendationMapper;

    @Override
    public List<Map<String, Object>> getRecommendedClasses(Integer userId, Integer page, Integer pageSize, String category) {
        Integer start = (page - 1) * pageSize;
        
        // 首先尝试基于用户偏好的推荐
        List<Map<String, Object>> recommendedClasses = recommendationMapper.selectByUserPreferences(userId, start, pageSize, category);
        
        // 如果基于偏好的推荐不足，则使用热门课程作为补充
        if (recommendedClasses.size() < pageSize) {
            Integer remaining = pageSize - recommendedClasses.size();
            List<Map<String, Object>> popularClasses = recommendationMapper.selectPopularClasses(start, remaining, category);
            recommendedClasses.addAll(popularClasses);
        }
        
        return recommendedClasses;
    }

    @Transactional
    @Override
    public void submitFeedback(Integer userId, Integer classId, String feedback) throws Exception {
        // 检查参数
        if (feedback == null || (!"LIKE".equals(feedback) && !"DISLIKE".equals(feedback) && !"NEUTRAL".equals(feedback))) {
            throw new Exception("无效的反馈类型: " + feedback);
        }
        
        // 检查课程是否存在
        Map<String, Object> classInfo = recommendationMapper.selectClassById(classId);
        if (classInfo == null) {
            throw new Exception("课程不存在，ID: " + classId);
        }
        
        // 检查是否已存在反馈
        Map<String, Object> existingFeedback = recommendationMapper.selectFeedbackByUserAndClass(userId, classId);
        
        if (existingFeedback == null) {
            // 创建新的反馈记录
            Map<String, Object> newFeedback = new HashMap<>();
            newFeedback.put("user_id", userId);
            newFeedback.put("class_id", classId);
            newFeedback.put("feedback", feedback);
            newFeedback.put("created_at", LocalDateTime.now());
            newFeedback.put("updated_at", LocalDateTime.now());
            
            recommendationMapper.insertFeedback(newFeedback);
            System.out.println("创建新的反馈记录: userId=" + userId + ", classId=" + classId + ", feedback=" + feedback);
        } else {
            // 更新现有反馈记录
            System.out.println("existingFeedback内容: " + existingFeedback);
            
            Map<String, Object> updateParams = new HashMap<>();
            // 尝试多种方式获取反馈ID
            Object feedbackId = null;
            if (existingFeedback.containsKey("feedback_id")) {
                feedbackId = existingFeedback.get("feedback_id");
            } else if (existingFeedback.containsKey("id")) {
                feedbackId = existingFeedback.get("id");
            } else {
                // 如果所有尝试都失败，抛出详细异常
                StringBuilder sb = new StringBuilder();
                sb.append("无法找到反馈记录ID，现有字段: ");
                for (Map.Entry<String, Object> entry : existingFeedback.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
                }
                throw new Exception(sb.toString());
            }
            if (feedbackId == null) {
                throw new Exception("反馈记录ID为空，无法更新反馈记录");
            }
            updateParams.put("feedback_id", feedbackId.toString()); // 保证为字符串类型
            updateParams.put("feedback", feedback);
            updateParams.put("updated_at", LocalDateTime.now());
            recommendationMapper.updateFeedback(updateParams);
            System.out.println("更新现有反馈记录: feedback_id=" + feedbackId + ", feedback=" + feedback);
        }
    }

    @Override
    public Map<String, Object> getRecommendationAnalysis(Integer userId) {
        Map<String, Object> analysis = new HashMap<>();
        // 获取推荐统计
        Map<String, Object> stats = recommendationMapper.selectRecommendationStats(userId);
        if (stats != null) {
            stats.entrySet().removeIf(e -> e.getKey() == null);
        }
        // 获取用户兴趣分布
        List<Map<String, Object>> interestDistribution = recommendationMapper.selectInterestDistribution(userId);
        if (interestDistribution != null) {
            for (Map<String, Object> map : interestDistribution) {
                map.entrySet().removeIf(e -> e.getKey() == null);
            }
        }
        // 获取最近的反馈
        List<Map<String, Object>> recentFeedback = recommendationMapper.selectRecentFeedback(userId, 5);
        if (recentFeedback != null) {
            for (Map<String, Object> map : recentFeedback) {
                map.entrySet().removeIf(e -> e.getKey() == null);
            }
        }
        analysis.put("stats", stats);
        analysis.put("interestDistribution", interestDistribution);
        analysis.put("recentFeedback", recentFeedback);
        return analysis;
    }
}
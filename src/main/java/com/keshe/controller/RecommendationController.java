package com.keshe.controller;

import com.keshe.pojo.Result;
import com.keshe.server.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    /**
     * 获取推荐课程
     */
    @GetMapping("/classes")
    public Result<List<Map<String, Object>>> getRecommendedClasses(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String category) {
        // 在实际应用中，这里应该从Token中获取用户ID
        Integer userId = 1; // 为了示例，使用固定的用户ID
        
        List<Map<String, Object>> recommendedClasses = recommendationService.getRecommendedClasses(userId, page, pageSize, category);
        return Result.success(recommendedClasses);
    }

    /**
     * 提交推荐反馈
     */
    @PostMapping("/feedback")
    public Result<String> submitFeedback(@RequestBody Map<String, Object> params) {
        try {
            // 在实际应用中，这里应该从Token中获取用户ID
            Integer userId = 1; // 为了示例，使用固定的用户ID

            Object classIdObj = params.get("classId");
            Object feedbackObj = params.get("feedback");
            String feedback = feedbackObj != null ? feedbackObj.toString() : null;

            // 参数检查
            if (classIdObj == null) {
                return Result.error("课程ID不能为空");
            }
            if (!(classIdObj instanceof Integer)) {
                return Result.error("课程ID格式不正确");
            }
            if (feedback == null || feedback.isEmpty()) {
                return Result.error("反馈内容不能为空");
            }
            Integer classId = (Integer) classIdObj;
            recommendationService.submitFeedback(userId, classId, feedback);
            return Result.success("反馈提交成功");
        } catch (Exception e) {
            return Result.error("反馈提交失败: " + e.getMessage());
        }
    }

    /**
     * 获取推荐分析
     */
    @GetMapping("/analysis")
    public Result<Map<String, Object>> getRecommendationAnalysis() {
        // 在实际应用中，这里应该从Token中获取用户ID
        Integer userId = 1; // 为了示例，使用固定的用户ID
        
        Map<String, Object> analysis = recommendationService.getRecommendationAnalysis(userId);
        return Result.success(analysis);
    }
}
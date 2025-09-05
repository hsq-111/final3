package com.keshe.controller;

import com.keshe.pojo.Result;
import com.keshe.server.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user-preferences")
public class UserPreferenceController {

    @Autowired
    private UserPreferenceService userPreferenceService;

    /**
     * 获取用户偏好
     */
    @GetMapping
    public Result<List<Map<String, Object>>> getUserPreferences(@RequestParam(required = false) Integer userId) {
        // 在实际应用中，这里应该从Token中获取用户ID
        // 如果没有提供userId，则使用当前登录用户的ID
        if (userId == null) {
            userId = 1; // 为了示例，使用固定的用户ID
        }
        
        List<Map<String, Object>> preferences = userPreferenceService.getUserPreferences(userId);
        return Result.success(preferences);
    }

    /**
     * 更新用户偏好
     */
    @PutMapping
    public Result<Map<String, Object>> updateUserPreference(@RequestBody Map<String, Object> params) {
        // 在实际应用中，这里应该从Token中获取用户ID
        Integer userId = 1; // 为了示例，使用固定的用户ID
        
        try {
            Map<String, Object> preference = userPreferenceService.updateUserPreference(userId, params);
            return Result.success(preference);
        } catch (Exception e) {
            return Result.error("偏好更新失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户偏好分析
     */
    @GetMapping("/analysis")
    public Result<Map<String, Object>> getUserPreferenceAnalysis(@RequestParam(required = false) Integer userId) {
        // 在实际应用中，这里应该从Token中获取用户ID
        if (userId == null) {
            userId = 1; // 为了示例，使用固定的用户ID
        }
        
        Map<String, Object> analysis = userPreferenceService.getUserPreferenceAnalysis(userId);
        return Result.success(analysis);
    }
}
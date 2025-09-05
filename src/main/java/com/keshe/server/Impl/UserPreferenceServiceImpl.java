package com.keshe.server.Impl;

import com.keshe.mapper.UserPreferenceMapper;
import com.keshe.pojo.Userpreference;
import com.keshe.server.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    @Autowired
    private UserPreferenceMapper userPreferenceMapper;

    @Override
    public List<Map<String, Object>> getUserPreferences(Integer userId) {
        return userPreferenceMapper.selectByUserId(userId);
    }

    @Transactional
    @Override
    public Map<String, Object> updateUserPreference(Integer userId, Map<String, Object> params) throws Exception {
        // 检查参数
        if (!params.containsKey("facility_type") || !params.containsKey("frequency")) {
            throw new Exception("参数不完整");
        }
        
        String facilityType = (String) params.get("facility_type");
        String frequency = (String) params.get("frequency");
        
        // 检查是否已存在该类型的偏好
        List<Map<String, Object>> existingPreferences = userPreferenceMapper.selectByUserIdAndType(userId, facilityType);
        
        if (existingPreferences.isEmpty()) {
            // 创建新的偏好记录
            Map<String, Object> newPreference = new HashMap<>();
            newPreference.put("user_id", userId);
            newPreference.put("facility_type", facilityType);
            newPreference.put("frequency", frequency);
            newPreference.put("created_at", LocalDateTime.now());
            newPreference.put("updated_at", LocalDateTime.now());
            
            userPreferenceMapper.insert(newPreference);
            
            // 获取新创建的偏好
            existingPreferences = userPreferenceMapper.selectByUserIdAndType(userId, facilityType);
            return existingPreferences.get(0);
        } else {
            // 更新现有偏好记录
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("preference_id", existingPreferences.get(0).get("id"));
            updateParams.put("frequency", frequency);
            updateParams.put("updated_at", LocalDateTime.now());
            
            userPreferenceMapper.update(updateParams);
            
            // 获取更新后的偏好
            existingPreferences = userPreferenceMapper.selectByUserIdAndType(userId, facilityType);
            return existingPreferences.get(0);
        }
    }

    @Override
    public Map<String, Object> getUserPreferenceAnalysis(Integer userId) {
        Map<String, Object> analysis = new HashMap<>();
        
        // 获取用户偏好
        List<Map<String, Object>> preferences = userPreferenceMapper.selectByUserId(userId);
        
        // 计算偏好统计
        Map<String, Integer> preferenceStats = new HashMap<>();
        for (Map<String, Object> preference : preferences) {
            String facilityType = (String) preference.get("facilityType");
            preferenceStats.put(facilityType, preferenceStats.getOrDefault(facilityType, 0) + 1);
        }
        
        // 获取推荐课程
        List<Map<String, Object>> recommendedClasses = userPreferenceMapper.selectRecommendedClasses(userId);
        
        analysis.put("preferences", preferences);
        analysis.put("stats", preferenceStats);
        analysis.put("recommendedClasses", recommendedClasses);
        
        return analysis;
    }
}
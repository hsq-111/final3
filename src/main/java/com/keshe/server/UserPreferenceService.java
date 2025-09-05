package com.keshe.server;

import java.util.List;
import java.util.Map;

public interface UserPreferenceService {
    /**
     * 获取用户偏好列表
     */
    List<Map<String, Object>> getUserPreferences(Integer userId);

    /**
     * 更新用户偏好
     */
    Map<String, Object> updateUserPreference(Integer userId, Map<String, Object> params) throws Exception;

    /**
     * 获取用户偏好分析
     */
    Map<String, Object> getUserPreferenceAnalysis(Integer userId);
}
package com.keshe.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserPreferenceMapper {
    /**
     * 根据用户ID查询偏好
     */
    @MapKey("id") // 使用偏好ID作为键
    List<Map<String, Object>> selectByUserId(Integer userId);

    /**
     * 根据用户ID和设施类型查询偏好
     */
    @MapKey("id") // 使用偏好ID作为键
    List<Map<String, Object>> selectByUserIdAndType(Integer userId, String facilityType);

    /**
     * 插入新偏好
     */
    void insert(Map<String, Object> preference);

    /**
     * 更新偏好信息
     */
    void update(Map<String, Object> params);

    /**
     * 查询推荐课程
     */
    @MapKey("classId") // 使用课程ID作为键
    List<Map<String, Object>> selectRecommendedClasses(Integer userId);
}

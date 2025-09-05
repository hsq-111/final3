package com.keshe.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationMapper {
    /**
     * 查询预约列表
     */
    @MapKey("id") // 使用预约ID作为键
    List<Map<String, Object>> selectList(Integer start, Integer pageSize, String status, Integer userId);

    /**
     * 根据用户ID和课程ID查询预约
     */
    @MapKey("id") // 使用预约ID作为键
    List<Map<String, Object>> selectByUserIdAndClassId(Integer userId, Integer classId);

    /**
     * 查询课程参与者数量
     */
    Integer selectParticipantCount(Integer classId);

    /**
     * 查询课程容量
     */
    @MapKey("classId") // 使用课程ID作为键
    Map<String, Object> selectClassCapacity(Integer classId);

    /**
     * 插入新预约
     */
    void insert(Map<String, Object> reservation);

    /**
     * 根据ID查询预约
     */
    @MapKey("id") // 使用预约ID作为键
    Map<String, Object> selectById(Integer id);

    /**
     * 更新预约信息
     */
    void update(Map<String, Object> params);
}

package com.keshe.server;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReservationService {
    /**
     * 获取预约列表
     */
    List<Map<String, Object>> getReservationList(Integer page, Integer pageSize, String status, Integer userId);

    /**
     * 创建新预约
     */
    Map<String, Object> createReservation(Integer userId, Integer classId, LocalDateTime startTime, LocalDateTime endTime) throws Exception;

    /**
     * 获取预约详情
     */
    Map<String, Object> getReservationById(Integer id);

    /**
     * 更新预约信息
     */
    Map<String, Object> updateReservation(Integer id, Map<String, Object> params) throws Exception;

    /**
     * 取消预约
     */
    void cancelReservation(Integer id) throws Exception;
}
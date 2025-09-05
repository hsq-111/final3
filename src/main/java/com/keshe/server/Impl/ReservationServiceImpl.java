package com.keshe.server.Impl;

import com.keshe.mapper.ReservationMapper;
import com.keshe.pojo.Reservation;
import com.keshe.server.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public List<Map<String, Object>> getReservationList(Integer page, Integer pageSize, String status, Integer userId) {
        Integer start = (page - 1) * pageSize;
        return reservationMapper.selectList(start, pageSize, status, userId);
    }

    @Transactional
    @Override
    public Map<String, Object> createReservation(Integer userId, Integer classId, LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        // 检查用户是否已经预约了该课程
        List<Map<String, Object>> existingReservations = reservationMapper.selectByUserIdAndClassId(userId, classId);
        if (!existingReservations.isEmpty()) {
            throw new Exception("您已经预约了该课程");
        }

        // 检查课程是否已满
        Integer participantCount = reservationMapper.selectParticipantCount(classId);
        Map<String, Object> classInfo = reservationMapper.selectClassCapacity(classId);
        if (classInfo == null) {
            throw new Exception("课程不存在，ID: " + classId);
        }
        
        // 增强检查：确保capacity字段存在且不为null
        Object capacityObj = classInfo.get("capacity");
        if (capacityObj == null) {
            throw new Exception("课程容量信息异常，无法获取课程容量信息");
        }
        
        if (!(capacityObj instanceof Integer)) {
            throw new Exception("课程容量信息异常，容量字段类型不正确");
        }
        
        Integer capacity = (Integer) capacityObj;
        if (participantCount != null && participantCount >= capacity) {
            throw new Exception("课程已满");
        }

        // 创建预约Map
        Map<String, Object> reservationMap = new HashMap<>();
        reservationMap.put("user_id", userId);
        reservationMap.put("facility_id", classId); // 使用facility_id存储课程ID
        reservationMap.put("start_time", startTime);
        reservationMap.put("end_time", endTime);
        reservationMap.put("status", "CONFIRMED");
        reservationMap.put("created_at", LocalDateTime.now());
        reservationMap.put("updated_at", LocalDateTime.now());
        
        reservationMapper.insert(reservationMap);
        
        // 返回预约详情
        // 由于insert后无法直接获取自增ID，我们需要查询最新的预约
        List<Map<String, Object>> latestReservations = reservationMapper.selectByUserIdAndClassId(userId, classId);
        if (!latestReservations.isEmpty()) {
            Integer reservationId = (Integer) latestReservations.get(0).get("id");
            return getReservationById(reservationId);
        }
        
        throw new Exception("预约创建成功，但无法获取预约详情");
    }

    @Override
    public Map<String, Object> getReservationById(Integer id) {
        return reservationMapper.selectById(id);
    }

    @Transactional
    @Override
    public Map<String, Object> updateReservation(Integer id, Map<String, Object> params) throws Exception {
        // 检查预约是否存在
        Map<String, Object> existingReservation = reservationMapper.selectById(id);
        if (existingReservation == null) {
            throw new Exception("预约不存在");
        }

        // 更新预约信息
        Map<String, Object> updateParams = new HashMap<>();
        updateParams.put("id", id);
        
        if (params.containsKey("status")) {
            updateParams.put("status", params.get("status"));
        }
        
        if (params.containsKey("startTime")) {
            updateParams.put("startTime", params.get("startTime"));
        }
        
        if (params.containsKey("endTime")) {
            updateParams.put("endTime", params.get("endTime"));
        }
        
        updateParams.put("updatedAt", LocalDateTime.now());
        reservationMapper.update(updateParams);
        
        // 返回更新后的预约详情
        return getReservationById(id);
    }

    @Transactional
    @Override
    public void cancelReservation(Integer id) throws Exception {
        // 检查预约是否存在
        Map<String, Object> existingReservation = reservationMapper.selectById(id);
        if (existingReservation == null) {
            throw new Exception("预约不存在");
        }

        // 检查预约状态
        String status = (String) existingReservation.get("status");
        if ("CANCELLED".equals(status)) {
            throw new Exception("预约已经被取消");
        }

        // 取消预约
        Map<String, Object> updateParams = new HashMap<>();
        updateParams.put("id", id);
        updateParams.put("status", "CANCELLED");
        updateParams.put("updatedAt", LocalDateTime.now());
        reservationMapper.update(updateParams);
    }
}
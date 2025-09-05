package com.keshe.controller;

import com.keshe.pojo.Reservation;
import com.keshe.pojo.Result;
import com.keshe.server.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * 获取预约列表
     */
    @GetMapping
    public Result<List<Map<String, Object>>> getReservationList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer userId) {
        List<Map<String, Object>> reservations = reservationService.getReservationList(page, pageSize, status, userId);
        return Result.success(reservations);
    }

    /**
     * 创建新预约
     */
    @PostMapping
    public Result<Map<String, Object>> createReservation(@RequestBody Map<String, Object> params) {
        try {
            // 参数检查
            Object classIdObj = params.get("classId");
            String startTimeStr = (String) params.get("startTime");
            String endTimeStr = (String) params.get("endTime");
            
            if (classIdObj == null) {
                return Result.error("课程ID不能为空");
            }
            
            if (!(classIdObj instanceof Integer)) {
                return Result.error("课程ID格式不正确");
            }
            
            Integer classId = (Integer) classIdObj;
            
            LocalDateTime startTime = null;
            LocalDateTime endTime = null;
            
            if (startTimeStr != null && !startTimeStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                startTime = LocalDateTime.parse(startTimeStr, formatter);
            }
            
            if (endTimeStr != null && !endTimeStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                endTime = LocalDateTime.parse(endTimeStr, formatter);
            }
            
            // 在实际应用中，这里应该从Token中获取用户ID
            // 为了示例，这里使用一个固定的用户ID
            Integer userId = 1;
            
            Map<String, Object> reservation = reservationService.createReservation(userId, classId, startTime, endTime);
            return Result.success(reservation);
        } catch (Exception e) {
            return Result.error("预约创建失败: " + e.getMessage());
        }
    }

    /**
     * 获取预约详情
     */
    @GetMapping("/{id}")
    public Result<Map<String, Object>> getReservationById(@PathVariable Integer id) {
        Map<String, Object> reservation = reservationService.getReservationById(id);
        if (reservation != null) {
            return Result.success(reservation);
        } else {
            return Result.error("预约不存在");
        }
    }

    /**
     * 更新预约信息
     */
    @PutMapping("/{id}")
    public Result<Map<String, Object>> updateReservation(@PathVariable Integer id, @RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> reservation = reservationService.updateReservation(id, params);
            return Result.success(reservation);
        } catch (Exception e) {
            return Result.error("预约更新失败: " + e.getMessage());
        }
    }

    /**
     * 取消预约
     */
    @PostMapping("/{id}/cancel")
    public Result<String> cancelReservation(@PathVariable Integer id) {
        try {
            reservationService.cancelReservation(id);
            return Result.success("预约已取消");
        } catch (Exception e) {
            return Result.error("预约取消失败: " + e.getMessage());
        }
    }
}
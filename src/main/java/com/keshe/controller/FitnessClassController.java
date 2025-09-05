package com.keshe.controller;

import com.keshe.pojo.FitnessClass;
import com.keshe.pojo.Result;
import com.keshe.server.FitnessClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fitness-classes")
public class FitnessClassController {

    @Autowired
    private FitnessClassService fitnessClassService;

    /**
     * 获取课程列表
     */
    @GetMapping
    public Result<List<FitnessClass>> getClassList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        List<FitnessClass> classes = fitnessClassService.getClassList(page, pageSize, category, search);
        return Result.success(classes);
    }

    /**
     * 创建新课程
     */
    @PostMapping
    public Result<FitnessClass> createClass(@RequestBody FitnessClass fitnessClass) {
        // 设置创建时间和更新时间
        fitnessClass.setCreatedAt(LocalDateTime.now());
        fitnessClass.setUpdatedAt(LocalDateTime.now());
        fitnessClass.setStatus("active");
        
        try {
            fitnessClassService.createClass(fitnessClass);
            return Result.success(fitnessClass);
        } catch (Exception e) {
            return Result.error("课程创建失败: " + e.getMessage());
        }
    }

    /**
     * 获取课程详情
     */
    @GetMapping("/{id}")
    public Result<FitnessClass> getClassById(@PathVariable Integer id) {
        FitnessClass fitnessClass = fitnessClassService.getClassById(id);
        if (fitnessClass != null) {
            return Result.success(fitnessClass);
        } else {
            return Result.error("课程不存在");
        }
    }

    /**
     * 更新课程信息
     */
    @PutMapping("/{id}")
    public Result<FitnessClass> updateClass(@PathVariable Integer id, @RequestBody FitnessClass fitnessClass) {
        fitnessClass.setClass_id(id);
        fitnessClass.setUpdatedAt(LocalDateTime.now());

        try {
            fitnessClassService.updateClass(fitnessClass);
            return Result.success(fitnessClass);
        } catch (Exception e) {
            return Result.error("课程更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除课程
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteClass(@PathVariable Integer id) {
        try {
            fitnessClassService.deleteClass(id);
            return Result.success("课程删除成功");
        } catch (Exception e) {
            return Result.error("课程删除失败: " + e.getMessage());
        }
    }

    /**
     * 获取课程参与者
     */
    @GetMapping("/{id}/participants")
    public Result<List<Map<String, Object>>> getClassParticipants(@PathVariable Integer id) {
        List<Map<String, Object>> participants = fitnessClassService.getClassParticipants(id);
        return Result.success(participants);
    }
}
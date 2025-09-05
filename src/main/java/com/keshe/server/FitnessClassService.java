package com.keshe.server;

import com.keshe.pojo.FitnessClass;

import java.util.List;
import java.util.Map;

public interface FitnessClassService {
    /**
     * 获取课程列表
     */
    List<FitnessClass> getClassList(Integer page, Integer pageSize, String category, String search);

    /**
     * 创建新课程
     */
    void createClass(FitnessClass fitnessClass);

    /**
     * 根据ID获取课程详情
     */
    FitnessClass getClassById(Integer id);

    /**
     * 更新课程信息
     */
    void updateClass(FitnessClass fitnessClass);

    /**
     * 删除课程
     */
    void deleteClass(Integer id);

    /**
     * 获取课程参与者
     */
    List<Map<String, Object>> getClassParticipants(Integer id);
}
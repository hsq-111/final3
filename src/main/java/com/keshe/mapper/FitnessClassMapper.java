package com.keshe.mapper;

import com.keshe.pojo.FitnessClass;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FitnessClassMapper {
    /**
     * 查询课程列表
     */
    List<FitnessClass> selectList(Integer start, Integer pageSize, String category, String search);

    /**
     * 插入新课程
     */
    void insert(FitnessClass fitnessClass);

    /**
     * 根据ID查询课程
     */
    FitnessClass selectById(Integer id);

    /**
     * 更新课程信息
     */
    void update(FitnessClass fitnessClass);

    /**
     * 删除课程
     */
    void delete(Integer id);

    /**
     * 查询课程参与者
     */
     @MapKey("userId") // 假设返回的Map中包含userId字段作为key
    List<Map<String, Object>> selectParticipants(Integer id);
}
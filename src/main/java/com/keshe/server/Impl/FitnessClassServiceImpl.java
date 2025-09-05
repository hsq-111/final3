package com.keshe.server.Impl;

import com.keshe.mapper.FitnessClassMapper;
import com.keshe.pojo.FitnessClass;
import com.keshe.server.FitnessClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FitnessClassServiceImpl implements FitnessClassService {

    @Autowired
    private FitnessClassMapper fitnessClassMapper;

    @Override
    public List<FitnessClass> getClassList(Integer page, Integer pageSize, String category, String search) {
        // 计算分页起始位置
        int start = (page - 1) * pageSize;
        return fitnessClassMapper.selectList(start, pageSize, category, search);
    }

    @Override
    public void createClass(FitnessClass fitnessClass) {
        fitnessClassMapper.insert(fitnessClass);
    }

    @Override
    public FitnessClass getClassById(Integer id) {
        return fitnessClassMapper.selectById(id);
    }

    @Override
    public void updateClass(FitnessClass fitnessClass) {
        fitnessClassMapper.update(fitnessClass);
    }

    @Override
    public void deleteClass(Integer id) {
        fitnessClassMapper.delete(id);
    }

    @Override
    public List<Map<String, Object>> getClassParticipants(Integer id) {
        return fitnessClassMapper.selectParticipants(id);
    }
}
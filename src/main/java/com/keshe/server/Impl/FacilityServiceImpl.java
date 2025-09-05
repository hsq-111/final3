package com.keshe.server.Impl;

import com.keshe.mapper.FacilityMapper;
import com.keshe.pojo.Facility;
import com.keshe.server.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {
    
    private static final Logger logger = LoggerFactory.getLogger(FacilityServiceImpl.class);
    
    @Autowired
    private FacilityMapper facilityMapper;

    @Override
    public List<Facility> getAllFacilities() {
        try {
            logger.info("获取所有设施列表");
            List<Facility> facilities = facilityMapper.findAll();
            logger.info("成功获取到 {} 个设施", facilities.size());
            return facilities;
        } catch (Exception e) {
            logger.error("获取设施列表时发生错误", e);
            throw new RuntimeException("数据库查询失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Facility getFacilityById(Integer id) {
        try {
            logger.info("根据ID获取设施: {}", id);
            Facility facility = facilityMapper.findById(id);
            if (facility != null) {
                logger.info("成功获取设施: {}", facility.getName());
            } else {
                logger.warn("未找到ID为 {} 的设施", id);
            }
            return facility;
        } catch (Exception e) {
            logger.error("根据ID获取设施时发生错误, ID: {}", id, e);
            throw new RuntimeException("数据库查询失败: " + e.getMessage(), e);
        }
    }

    @Override
    public void addFacility(Facility facility) {
        try {
            logger.info("添加新设施: {}", facility.getName());
            facilityMapper.insert(facility);
            logger.info("成功添加设施，ID: {}", facility.getFacilityId());
        } catch (Exception e) {
            logger.error("添加设施时发生错误: {}", facility.getName(), e);
            throw new RuntimeException("数据库插入失败: " + e.getMessage(), e);
        }
    }

    @Override
    public int updateFacility(Facility facility) {
        try {
            logger.info("更新设施: ID={}, Name={}", facility.getFacilityId(), facility.getName());
            int result = facilityMapper.update(facility);
            logger.info("设施更新完成，影响行数: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("更新设施时发生错误: ID={}", facility.getFacilityId(), e);
            throw new RuntimeException("数据库更新失败: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFacility(Integer id) {
        try {
            logger.info("删除设施: ID={}", id);
            facilityMapper.deleteById(id);
            logger.info("成功删除设施: ID={}", id);
        } catch (Exception e) {
            logger.error("删除设施时发生错误: ID={}", id, e);
            throw new RuntimeException("数据库删除失败: " + e.getMessage(), e);
        }
    }
}

package com.keshe.integration;

import com.keshe.pojo.Facility;
import com.keshe.mapper.FacilityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FacilityMapperIntegrationTest {

    @Autowired
    private FacilityMapper facilityMapper;

    @Test
    public void testFindAll() {
        // 这个测试验证数据库连接和基本查询是否正常
        try {
            List<Facility> facilities = facilityMapper.findAll();
            assertNotNull(facilities);
            // 不要求有数据，只要能正常查询即可
        } catch (Exception e) {
            fail("获取所有设施时出现异常: " + e.getMessage());
        }
    }

    @Test
    public void testInsertAndFindById() {
        // 创建测试设施
        Facility facility = new Facility();
        facility.setName("测试设施");
        facility.setType("测试类型");
        facility.setLocation("测试位置");
        facility.setCapacity(10);
        facility.setStatus("AVAILABLE");
        facility.setCreatedAt(LocalDateTime.now());
        facility.setUpdatedAt(LocalDateTime.now());

        try {
            // 插入
            facilityMapper.insert(facility);
            assertNotNull(facility.getFacilityId());

            // 查询
            Facility found = facilityMapper.findById(facility.getFacilityId());
            assertNotNull(found);
            assertEquals("测试设施", found.getName());
            assertEquals("测试类型", found.getType());
            assertEquals("测试位置", found.getLocation());
            assertEquals(Integer.valueOf(10), found.getCapacity());
            assertEquals("AVAILABLE", found.getStatus());

        } catch (Exception e) {
            fail("插入或查询设施时出现异常: " + e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        // 首先插入一个设施
        Facility facility = new Facility();
        facility.setName("原始设施");
        facility.setType("原始类型");
        facility.setLocation("原始位置");
        facility.setCapacity(5);
        facility.setStatus("AVAILABLE");
        facility.setCreatedAt(LocalDateTime.now());
        facility.setUpdatedAt(LocalDateTime.now());

        try {
            facilityMapper.insert(facility);
            assertNotNull(facility.getFacilityId());

            // 更新
            facility.setName("更新设施");
            facility.setStatus("MAINTENANCE");
            facility.setUpdatedAt(LocalDateTime.now());

            int result = facilityMapper.update(facility);
            assertEquals(1, result);

            // 验证更新
            Facility updated = facilityMapper.findById(facility.getFacilityId());
            assertEquals("更新设施", updated.getName());
            assertEquals("MAINTENANCE", updated.getStatus());

        } catch (Exception e) {
            fail("更新设施时出现异常: " + e.getMessage());
        }
    }

    @Test
    public void testDelete() {
        // 首先插入一个设施
        Facility facility = new Facility();
        facility.setName("待删除设施");
        facility.setType("测试类型");
        facility.setLocation("测试位置");
        facility.setCapacity(1);
        facility.setStatus("AVAILABLE");
        facility.setCreatedAt(LocalDateTime.now());
        facility.setUpdatedAt(LocalDateTime.now());

        try {
            facilityMapper.insert(facility);
            assertNotNull(facility.getFacilityId());

            // 删除
            facilityMapper.deleteById(facility.getFacilityId());

            // 验证删除
            Facility deleted = facilityMapper.findById(facility.getFacilityId());
            assertNull(deleted);

        } catch (Exception e) {
            fail("删除设施时出现异常: " + e.getMessage());
        }
    }
}
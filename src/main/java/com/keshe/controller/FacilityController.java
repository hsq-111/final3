package com.keshe.controller;

import com.keshe.pojo.Facility;
import com.keshe.pojo.Result;
import com.keshe.server.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/facilities")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    /**
     * 查询所有设施
     * @return
     */
    @GetMapping
    public Result<List<Facility>> getAllFacilities() {
        List<Facility> facilities = facilityService.getAllFacilities();
        return Result.success(facilities);
    }

    /**
     * 根据ID查询设施
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Facility> getFacilityById(@PathVariable Integer id) {
        Facility facility = facilityService.getFacilityById(id);
        System.out.println("查询结果：" + facility);
        if (facility != null) {
            return Result.success(facility);
        } else {
            return Result.error("设施不存在");
        }
    }

    /**
     * 添加设施
     * @param facility
     * @return
     */
    @PostMapping
    public Result<String> addFacility(@RequestBody Facility facility) {
        // 设置创建时间和更新时间
        facility.setCreatedAt(LocalDateTime.now());
        facility.setUpdatedAt(LocalDateTime.now());

        try {
            facilityService.addFacility(facility);
            return Result.success("设施添加成功");
        } catch (Exception e) {
            return Result.error("设施添加失败: " + e.getMessage());
        }
    }

    /**
     * 更新设施
     * @param id
     * @param facility
     * @return
     */
    @PutMapping("/{id}")
    public Result<String> updateFacility(@PathVariable Integer id, @RequestBody Facility facility) {
        facility.setFacilityId(id);
        facility.setUpdatedAt(LocalDateTime.now());
        try {
            int result = facilityService.updateFacility(facility);
            if (result > 0) {
                return Result.success("设施更新成功");
            } else {
                return Result.error("设施不存在，更新失败");
            }
        } catch (Exception e) {
            return Result.error("设施更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除设施
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> deleteFacility(@PathVariable Integer id) {
        try {
            facilityService.deleteFacility(id);
            return Result.success("设施删除成功");
        } catch (Exception e) {
            return Result.error("设施删除失败: " + e.getMessage());
        }
    }
}
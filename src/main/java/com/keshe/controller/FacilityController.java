package com.keshe.controller;

import com.keshe.pojo.Facility;
import com.keshe.pojo.Result;
import com.keshe.server.FacilityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
        try {
            List<Facility> facilities = facilityService.getAllFacilities();
            return Result.success(facilities);
        } catch (Exception e) {
            return Result.error("获取设施列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询设施
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Facility> getFacilityById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return Result.error("设施ID无效");
        }
        
        try {
            Facility facility = facilityService.getFacilityById(id);
            if (facility != null) {
                return Result.success(facility);
            } else {
                return Result.error("设施不存在");
            }
        } catch (Exception e) {
            return Result.error("获取设施详情失败: " + e.getMessage());
        }
    }

    /**
     * 添加设施
     * @param facility
     * @param bindingResult
     * @return
     */
    @PostMapping
    public Result<String> addFacility(@Valid @RequestBody Facility facility, BindingResult bindingResult) {
        // 检查验证错误
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                    .orElse("输入数据有误");
            return Result.error(errorMsg);
        }

        // 设置创建时间和更新时间
        facility.setCreatedAt(LocalDateTime.now());
        facility.setUpdatedAt(LocalDateTime.now());
        
        // 设置默认状态
        if (facility.getStatus() == null || facility.getStatus().trim().isEmpty()) {
            facility.setStatus("AVAILABLE");
        }

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
        if (id == null || id <= 0) {
            return Result.error("设施ID无效");
        }
        
        // 检查设施是否存在
        try {
            Facility existingFacility = facilityService.getFacilityById(id);
            if (existingFacility == null) {
                return Result.error("设施不存在，更新失败");
            }
        } catch (Exception e) {
            return Result.error("检查设施是否存在时发生错误: " + e.getMessage());
        }
        
        facility.setFacilityId(id);
        facility.setUpdatedAt(LocalDateTime.now());
        
        try {
            int result = facilityService.updateFacility(facility);
            if (result > 0) {
                return Result.success("设施更新成功");
            } else {
                return Result.error("设施更新失败");
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
        if (id == null || id <= 0) {
            return Result.error("设施ID无效");
        }
        
        // 检查设施是否存在
        try {
            Facility existingFacility = facilityService.getFacilityById(id);
            if (existingFacility == null) {
                return Result.error("设施不存在，删除失败");
            }
        } catch (Exception e) {
            return Result.error("检查设施是否存在时发生错误: " + e.getMessage());
        }
        
        try {
            facilityService.deleteFacility(id);
            return Result.success("设施删除成功");
        } catch (Exception e) {
            return Result.error("设施删除失败: " + e.getMessage());
        }
    }
}
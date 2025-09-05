package com.keshe.pojo;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Data
public class Facility {
    private Integer facilityId;
    
    @NotBlank(message = "设施名称不能为空")
    private String name;
    
    @NotBlank(message = "设施类型不能为空")
    private String type;
    
    @NotBlank(message = "设施位置不能为空")
    private String location;
    
    @NotNull(message = "容量不能为空")
    @Min(value = 1, message = "容量必须大于0")
    private Integer capacity;
    
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

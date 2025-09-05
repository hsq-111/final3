package com.keshe.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Facility {
    private Integer facilityId;
    private String name;
    private String type;
    private String location;
    private Integer capacity;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.keshe.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FitnessClass {
    private Integer class_id;
    private String name;
    private String type;
    private String instructor;
    private LocalDateTime time;
    private String location;
    private Integer capacity;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

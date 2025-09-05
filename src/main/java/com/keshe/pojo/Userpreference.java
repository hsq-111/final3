package com.keshe.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Userpreference {
    private Integer preference_id;
    private Integer user_id;
    private String facility_type;
    private String frequency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.keshe.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reservation {
    private Integer reservation_id;
    private Integer user_id;
    private Integer facility_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

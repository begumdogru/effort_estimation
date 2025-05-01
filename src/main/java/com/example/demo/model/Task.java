package com.example.demo.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Task {
    private Long id;
    private String taskName;
    private TechnologyType technology;
    private Timestamp startDate;
    private Timestamp endDate;
    private Double estimatedEffort;
    private String description;
    private Double actualEffort;
    private Long projectId;

}

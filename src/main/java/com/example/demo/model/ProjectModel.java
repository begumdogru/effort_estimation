package com.example.demo.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ProjectModel {
    private Long id;
    private String projectName;
    private Integer storyPoint;
    private Category category;
    private Double complexity;
    private Double uncertainty;
    private RiskCategory riskManagement;
    private TechnologyType technology;
    private Double estimatedEffort;
    private Double actualEffort;
    private Timestamp createdDate;
    private List<Task> tasks;
}

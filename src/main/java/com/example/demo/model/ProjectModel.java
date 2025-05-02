package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
public class ProjectModel {
    @Id
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
    private Timestamp updatedDate;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;
}

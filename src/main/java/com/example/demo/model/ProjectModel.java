package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;


import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "project")
@Data
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String projectName;
    @Column(nullable = false)
    private Integer storyPoint;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    private Double complexity;
    private Double uncertainty;
    @Enumerated(EnumType.STRING)
    private RiskCategory riskManagement;
    @Enumerated(EnumType.STRING)
    @Column(name = "technology")
    private TechnologyType technology;
    @Column(nullable = false)
    private Double estimatedEffort;
    private Double actualEffort;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;
}

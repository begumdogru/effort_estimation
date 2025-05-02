package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "tasks")
public class Task {
    @Id
    private Long id;
    private String taskName;
    private TechnologyType technology;
    private Timestamp startDate;
    private Timestamp endDate;
    private Double estimatedEffort;
    private String description;
    private Double actualEffort;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectModel project;

}

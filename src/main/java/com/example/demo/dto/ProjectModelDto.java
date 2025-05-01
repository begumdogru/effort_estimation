package com.example.demo.dto;

import com.example.demo.model.Category;
import com.example.demo.model.RiskCategory;
import com.example.demo.model.Task;
import com.example.demo.model.TechnologyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectModelDto {
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

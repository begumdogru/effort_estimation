package com.example.demo.dto;

import com.example.demo.model.TechnologyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String taskName;
    private TechnologyType technology;
    private Timestamp startDate;
    private Timestamp endDate;
    private Double estimatedEffort;
    private String description;
    private Double actualEffort;
    private Long projectId;
}

package com.example.demo.service;

import com.example.demo.model.ProjectModel;
import com.example.demo.model.Task;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    private ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Optional<ProjectModel> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
    public List<ProjectModel> getProjects() {
        return projectRepository.findAll();
    }
    public ProjectModel createProject(ProjectModel projectModel) {
        return projectRepository.save(projectModel);
    }

}

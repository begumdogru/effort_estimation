package com.example.demo.service;

import com.example.demo.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {
    private ProjectRepository projectRepository;
    public Optional<Object> getProjectById(Long id) {
        return Optional.of(projectRepository.findById(id));
    }
}

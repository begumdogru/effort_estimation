package com.example.demo.controller;

import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    //There should be create project, update project, get project by id, get all projects, delete project and
    //send a request to an ai bot to get the estimation of the project
    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id).
                map(projectModel -> new ResponseEntity<>(projectModel, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

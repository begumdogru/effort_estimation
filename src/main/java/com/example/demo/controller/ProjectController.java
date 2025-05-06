package com.example.demo.controller;

import com.example.demo.model.ProjectModel;
import com.example.demo.model.Task;
import com.example.demo.service.AiBotService;
import com.example.demo.service.ProjectService;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    //There should be create project, update project, get project by id, get all projects, delete project and
    //send a request to an ai bot to get the estimation of the project
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AiBotService aiBotService;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectModel> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id).
                map(projectModel -> new ResponseEntity<>(projectModel, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getProjects() {
        return ResponseEntity.ok(projectService.getProjects());
    }
    @PostMapping("/createProject")
    public ResponseEntity<Object> createProject(@RequestBody ProjectModel projectModel) {
        return ResponseEntity.ok(projectService.createProject(projectModel));
    }
    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }
    @PostMapping("/getEstimation")
    public ResponseEntity<Object> getEstimation(@RequestBody ProjectModel projectModel){
        List<ProjectModel> projects = projectService.getProjects();
        return ResponseEntity.ok(aiBotService.getEstimation(projectModel, projects));
    }
}

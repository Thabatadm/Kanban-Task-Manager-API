package com.example.demo.controller;

import com.example.demo.dto.ProjectCreationRequest;
import com.example.demo.model.Project;
import com.example.demo.dto.AddMemberRequest;
import com.example.demo.service.ProjectService;
import com.example.demo.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectUserService projectUserService;

    // --- PROYECTOS ---

    // Cambiamos @RequestBody Project por @RequestBody ProjectCreationRequest
    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreationRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping
    public ResponseEntity<List<Project>> printAllProjects() {
        return ResponseEntity.ok(projectService.getUserProjects());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectCreationRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request.getName(),request.getDescription()));
    }

    // --- MIEMBROS ---

    @PostMapping("/{projectId}/members")
    public ResponseEntity<Void> addMember(
            @PathVariable Long projectId,
            @RequestBody AddMemberRequest memberDto) {

        projectUserService.addMemberToProject(projectId, memberDto.getUserId(), memberDto.getRole());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long projectId, @PathVariable Long userId){
        projectUserService.removeUserFromProject(projectId, userId);
        return ResponseEntity.noContent().build();
    }
}
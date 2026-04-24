package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;

    public ProjectService(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    public Project createProject(Project project) {

        User owner = userService.getCurrentUser();

        project.setCreatedAt(LocalDateTime.now());



        if (project.getMembers() == null) {
            project.setMembers(new ArrayList<>());
        }

        project.getMembers().add(owner);

        return projectRepository.save(project);
    }

    public Project updateProject(Long projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.delete(project);

    }

    public List<Project> getAllProjectsByMembers(){
        User user = userService.getCurrentUser();

        return projectRepository.findByMembers_Id(user.getId());
    }
}
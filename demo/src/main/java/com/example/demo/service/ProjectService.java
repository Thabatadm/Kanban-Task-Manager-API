package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.ProjectUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    private final UserService userService;

    public ProjectService(ProjectRepository projectRepository,
                          ProjectUserRepository projectUserRepository,
                          UserService userService) {
        this.projectRepository = projectRepository;
        this.projectUserRepository = projectUserRepository;
        this.userService = userService;
    }

    @Transactional
    public Project createProject(Project project) {
        User owner = userService.getCurrentUser();
        project.setCreatedAt(LocalDateTime.now());


        Project savedProject = projectRepository.save(project);

        ProjectUser ownerRelation = new ProjectUser();
        ownerRelation.setUser(owner);
        ownerRelation.setProject(savedProject);
        ownerRelation.setRole(Role.MASTER);

        projectUserRepository.save(ownerRelation);

        if (savedProject.getMembers() == null) {
            savedProject.setMembers(new ArrayList<>());
        }
        savedProject.getMembers().add(ownerRelation);

        return savedProject;
    }

    public Project updateProject(Long projectId, String name, String description){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(name);
        project.setDescription(description);

        return projectRepository.save(project);
    }

    public void deleteProject(Long projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        projectRepository.delete(project);

    }

    public List<Project> getUserProjects() {

        User user = userService.getCurrentUser();

        return projectUserRepository.findByUser(user)
                .stream()
                .map(ProjectUser::getProject)
                .toList();
    }
}
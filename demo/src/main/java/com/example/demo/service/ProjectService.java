package com.example.demo.service;

import com.example.demo.dto.ProjectCreationRequest;
import com.example.demo.model.*;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.ProjectUserRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUserService projectUserService;
    private final ProjectUserRepository projectUserRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    public ProjectService(ProjectRepository projectRepository,
                          ProjectUserService projectUserService,
                          ProjectUserRepository projectUserRepository,
                          UserRepository userRepository,
                          UserService userService) {
        this.projectRepository = projectRepository;
        this.projectUserService = projectUserService;
        this.projectUserRepository = projectUserRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public Project createProject(ProjectCreationRequest request) {
        User owner = userService.getCurrentUser();

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setCreatedAt(LocalDateTime.now());

        Project savedProject = projectRepository.save(project);

        ProjectUser ownerRelation = new ProjectUser();
        ownerRelation.setUser(owner);
        ownerRelation.setProject(savedProject);
        ownerRelation.setRole(Role.MASTER);
        projectUserRepository.save(ownerRelation);

        if (request.getDeveloperIds() != null && !request.getDeveloperIds().isEmpty()) {
            for (Long userId : request.getDeveloperIds()) {
                User guest = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

                ProjectUser devRelation = new ProjectUser();
                devRelation.setUser(guest);
                devRelation.setProject(savedProject);
                devRelation.setRole(Role.DEVELOPER);

                projectUserRepository.save(devRelation);
            }
        }

        return savedProject;
    }

    @Transactional
    public Project updateProject(Long projectId, String name, String description) {
        // Ahora funcionará porque projectUserService ya no es null
        projectUserService.validateMasterPermission(projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (name != null) project.setName(name);
        if (description != null) project.setDescription(description);

        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectUserService.validateMasterPermission(projectId);

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
package com.example.demo.service;

import com.example.demo.model.Project;
import com.example.demo.model.ProjectUser;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectUserService {

    private final ProjectUserRepository projectUserRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ProjectUserService(ProjectUserRepository projectUserRepository,
                              ProjectRepository projectRepository,
                              UserRepository userRepository,
                              UserService userService) {
        this.projectUserRepository = projectUserRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public void addMemberToProject(Long projectId, Long userId, Role role) {
        validateMasterPermission(projectId);


        projectUserRepository.findByProjectIdAndUserId(projectId, userId)
                .ifPresent(m -> { throw new RuntimeException("User is already a member"); });

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User to add not found"));

        ProjectUser newMember = new ProjectUser();
        newMember.setProject(project);
        newMember.setUser(user);
        newMember.setRole(role);

        projectUserRepository.save(newMember);
    }

    public void validateMasterPermission(Long projectId) {
        User currentUser = userService.getCurrentUser();
        ProjectUser relation = projectUserRepository
                .findByProjectIdAndUserId(projectId, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("You do not belong to this project"));

        if (relation.getRole() != Role.MASTER) {
            throw new RuntimeException("Permission denied: Only the MASTER can perform this action");
        }
    }

    @Transactional
    public void removeUserFromProject(Long projectId, Long userId) {
        validateMasterPermission(projectId);

        ProjectUser relation = projectUserRepository
                .findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (relation.getRole() == Role.MASTER) {
            throw new RuntimeException("Cannot remove the Master of the project");
        }

        projectUserRepository.delete(relation);


    }


}

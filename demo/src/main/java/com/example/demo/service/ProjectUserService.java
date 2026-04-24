package com.example.demo.service;

import com.example.demo.model.ProjectUser;
import com.example.demo.repository.ProjectUserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectUserService {

    private final ProjectUserRepository projectUserRepository;

    public ProjectUserService(ProjectUserRepository projectUserRepository) {
        this.projectUserRepository = projectUserRepository;
    }

    public void removeUserFromProject(Long projectId, Long userId) {
        ProjectUser relation = projectUserRepository
                .findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        projectUserRepository.delete(relation);


    }


}

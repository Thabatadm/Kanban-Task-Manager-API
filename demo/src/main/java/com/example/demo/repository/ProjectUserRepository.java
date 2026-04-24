package com.example.demo.repository;


import java.util.Optional;
import com.example.demo.model.ProjectUser;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {
    List<ProjectUser> findByUser(User user);

    Optional<ProjectUser> findByProjectIdAndUserId(Long projectId, Long userId);


}

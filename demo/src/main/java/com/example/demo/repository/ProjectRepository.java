package com.example.demo.repository;

import com.example.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    List<Project> findByMembers_User_Id(Long userId);

    Optional<Project> findById(Long projectId);
}

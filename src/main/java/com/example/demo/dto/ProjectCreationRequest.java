package com.example.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectCreationRequest {
    private String name;
    private String description;
    private List<Long> developerIds;
}
package com.example.demo.dto;

import com.example.demo.model.Priority;
import com.example.demo.model.Status;
import lombok.Data;

@Data
public class CardRequest {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
}
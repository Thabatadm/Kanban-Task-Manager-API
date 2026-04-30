package com.example.demo.dto;

import com.example.demo.model.Role;
import lombok.Data;

@Data
public class AddMemberRequest {
    private Long userId;
    private Role role;
}

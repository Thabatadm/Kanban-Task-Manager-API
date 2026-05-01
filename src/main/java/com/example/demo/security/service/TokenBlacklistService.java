package com.example.demo.security.service;

import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public void blackListToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isBlackListToken(String token) {
        return blacklistedTokens.contains(token);
    }
}

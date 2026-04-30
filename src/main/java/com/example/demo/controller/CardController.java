package com.example.demo.controller;

import com.example.demo.dto.CardRequest;
import com.example.demo.dto.CardUpdate;
import com.example.demo.model.Card;
import com.example.demo.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/create")
    public ResponseEntity<Card> createCard(
            @PathVariable Long projectId,
            @RequestBody CardRequest cardRequest) { // Usamos el DTO
        return ResponseEntity.ok(cardService.createCard(projectId, cardRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable Long id, @RequestBody CardUpdate cardUpdateDto) {
        return ResponseEntity.ok(cardService.updateCardDetails(id, cardUpdateDto));
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCardsByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(cardService.getCardsByProject(projectId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
package com.example.demo.service;

import com.example.demo.model.Card;
import com.example.demo.model.Status;
import com.example.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card card){
        card.setCreatedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());
        if (card.getStatus() == null){
            card.setStatus(Status.TO_DO);
        }

        if (card.getPosition() == null) {
            List<Card> existingCards = cardRepository.findByProjectIdOrderByPositionAsc(card.getProject().getId());
            card.setPosition(existingCards.size() + 1);
        }

        return cardRepository.save(card);

    }

    @Transactional
    public Card updateCardDetails(Long cardId, Card cardDetails) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));


        if (cardDetails.getTitle() != null) card.setTitle(cardDetails.getTitle());
        if (cardDetails.getDescription() != null) card.setDescription(cardDetails.getDescription());
        if (cardDetails.getPriority() != null) card.setPriority(cardDetails.getPriority());
        if (cardDetails.getDueDate() != null) card.setDueDate(cardDetails.getDueDate());
        if (cardDetails.getStatus() != null) card.setStatus(cardDetails.getStatus());
        if (cardDetails.getAssignee() != null) {card.setAssignee(cardDetails.getAssignee());}

        card.setUpdatedAt(LocalDateTime.now());

        return cardRepository.save(card);
    }

    public List<Card> getCardsbyProject(Long projectId){
        return cardRepository.findByProjectIdOrderByPositionAsc(projectId);

    }

    public void deleteCard(Long cardId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new RuntimeException("Card not found"));
        cardRepository.delete(card);

    }


}

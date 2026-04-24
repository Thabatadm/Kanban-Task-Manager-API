package com.example.demo.service;

import com.example.demo.model.Card;
import com.example.demo.model.Status;
import com.example.demo.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        return cardRepository.save(card);

    }

    public Card cardUpdate(Long cardId, Status newStatus){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));
        card.setUpdatedAt(LocalDateTime.now());
        card.setStatus(newStatus);

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

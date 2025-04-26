package com.leitner.leitner_system.infrastructure.in.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.leitner.leitner_system.application.dto.CardUserData;
import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.ports.primary.CardServicePort;
import com.leitner.leitner_system.domain.service.QuizzService;
import com.leitner.leitner_system.infrastructure.adapter.in.rest.CardController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
public class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CardServicePort cardService;

    @MockitoBean
    private QuizzService quizzService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllCards() throws Exception {
        Card card1 = new Card("Q1", "A1", "tag1");
        Card card2 = new Card("Q2", "A2", "tag2");

        when(cardService.getCards()).thenReturn(List.of(card1, card2));

        mockMvc.perform(get("/cards")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].question").value("Q1"))
                .andExpect(jsonPath("$[1].tag").value("tag2"));
        verify(cardService, times(1)).getCards();
    }

    @Test
    void shouldReturnCardById() throws Exception {
        UUID cardId = UUID.randomUUID();
        Card card = new Card("Q", "A", "tag");
        card.setId(cardId);

        when(cardService.getCardById(cardId)).thenReturn(card);

        mockMvc.perform(get("/cards/{id}", cardId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Q"))
                .andExpect(jsonPath("$.answer").value("A"))
                .andExpect(jsonPath("$.tag").value("tag"));
    }

    @Test
    void shouldTReturnNotFoundWhenCardIsNotFound() throws Exception {
        UUID cardId = UUID.randomUUID();
        Card card = new Card("Q", "A", "tag");
        card.setId(cardId);

        when(cardService.getCardById(cardId)).thenReturn(null);

        mockMvc.perform(get("/cards/{id}", cardId)).andExpect(status().isNotFound());
        verify(cardService, times(1)).getCardById(cardId);
    }

    @Test
    void shouldReturnCardsForQuizz() throws Exception {
        when(quizzService.hasTakenQuizToday(any())).thenReturn(false);

        Card card = new Card("Q", "A", "tag");
        when(cardService.getCardsForQuizz(any())).thenReturn(List.of(card));

        mockMvc.perform(get("/cards/quizz")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].question").value("Q"));
    }

    @Test
    void shouldAnswerCardCorrectly() throws Exception {
        UUID cardId = UUID.randomUUID();
        Card card = new Card("Q", "A", "tag");
        card.setId(cardId);

        when(cardService.answerCard(cardId, true)).thenReturn(card);

        mockMvc.perform(patch("/cards/{cardId}/answer", cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"isValid\": true }"))
                .andExpect(status().isNoContent());

        verify(cardService).answerCard(cardId, true);
    }

    @Test
    void shouldReturnNotFoundIfAnsweringUnknownCard() throws Exception {
        UUID cardId = UUID.randomUUID();

        when(cardService.answerCard(cardId, false)).thenReturn(null);

        mockMvc.perform(patch("/cards/{cardId}/answer", cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"isValid\": false }"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCardsByTagWhenTagIsProvided() throws Exception {
        List<Card> mockCards = List.of(new Card("Q", "A", "tag"));
        when(cardService.getCardsByTag(List.of("tag"))).thenReturn(mockCards);

        mockMvc.perform(get("/cards?tag=tag"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question").value("Q"));

        verify(cardService).getCardsByTag(List.of("tag"));
    }

    @Test
    void shouldReturn404WhenAnsweringUnknownCard() throws Exception {
        UUID cardId = UUID.randomUUID();
        when(cardService.answerCard(cardId, true)).thenReturn(null);

        mockMvc.perform(patch("/cards/" + cardId + "/answer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"valid\":true}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenQuestionIsNull() throws Exception {
        CardUserData invalidData = new CardUserData(null, "A", "tag");

        mockMvc.perform(post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest());
    }







}

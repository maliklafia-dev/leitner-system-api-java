package com.leitner.leitner_system.domain.services;

import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.ports.secondary.CardRepositoryPort;
import com.leitner.leitner_system.domain.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardServiceTests {
    private CardRepositoryPort cardRepository;
    private CardService cardService;
    private List<Card> cards;

    @BeforeEach
    public void setUp() {
        this.cardRepository = mock(CardRepositoryPort.class);
        this.cardService = new CardService(cardRepository);

        Card card1 = new Card("Q1", "A1", "tag1");
        Card card2 = new Card("Q2", "A2", "tag2");
        this.cards = Arrays.asList(card1, card2);
    }

    @Test
    void shouldReturnAllCards() {
        List<Card> expected = this.cards;

        when(this.cardRepository.findAllCards()).thenReturn(expected);

        List<Card> result = this.cardService.getCards();
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(this.cardRepository, times(1)).findAllCards();
    }

    @Test
    void shouldReturnCardById() {
        Card expectedCard = new Card("Q1", "A1", "tag1");

        when(this.cardRepository.findCardById(expectedCard.getId())).thenReturn(expectedCard);
        Card result = this.cardService.getCardById(expectedCard.getId());
        assertNotNull(result);
        assertEquals(expectedCard.getId(), result.getId());
        assertEquals(expectedCard.getQuestion(), result.getQuestion());
        assertEquals(expectedCard.getAnswer(), result.getAnswer());
        assertEquals(expectedCard.getTag(), result.getTag());
        verify(this.cardRepository, times(1)).findCardById(expectedCard.getId());
    }

    @Test
    void shouldCreateCardWithValidData() {
        Card expectedCard = new Card("Q1", "A1", "tag1");

        Card actualCard = this.cardService.createCard(expectedCard);
        assertNotNull(actualCard);
        assertEquals(expectedCard.getQuestion(), actualCard.getQuestion());
        assertEquals(expectedCard.getAnswer(), actualCard.getAnswer());
        assertEquals(expectedCard.getTag(), actualCard.getTag());
        verify(this.cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void shouldReturnNullWhenCreatingCardWithNullData() {
        Card result = cardService.createCard(null);
        assertNull(result);
        verify(cardRepository, never()).save(any());
    }

    @Test
    void shouldUpdateCardWithValidData() {
        Card oldCard = new Card("oldQ", "oldA", "oldTag");
        UUID cardId = UUID.randomUUID();

        when(this.cardRepository.findCardById(cardId)).thenReturn(oldCard);
        Card newCard = new Card("newQ", "newA", "newTag");

        Card cardToUpdate = this.cardService.updateCard(cardId, newCard);
        assertNotNull(cardToUpdate);
        assertEquals(oldCard.getId(), cardToUpdate.getId());
        assertEquals("newQ", cardToUpdate.getQuestion());
        assertEquals("newA", cardToUpdate.getAnswer());
        assertEquals("newTag", cardToUpdate.getTag());
        verify(this.cardRepository, times(1)).findCardById(cardId);
    }

    @Test
    void shouldSetToNextCardCategoryWhenAnswerCard_isCorrect_true() {
        Card card = new Card("Q1", "A1", "tag1");
        when(this.cardRepository.findCardById(card.getId())).thenReturn(card);
        this.cardService.answerCard(card.getId(), true);
        assertEquals("SECOND", card.getCategory());
        assertNotNull(card.getLastAnsweredAt());
        verify(this.cardRepository, times(1)).findCardById(card.getId());
    }

    @Test
    void shouldSetToFirstCardCategoryWhenAnswerCard_isCorrect_false() {
        Card card = new Card("Q1", "A1", "tag1");

        when(this.cardRepository.findCardById(card.getId())).thenReturn(card);
        this.cardService.answerCard(card.getId(), false);
        assertEquals("FIRST", card.getCategory());
        assertNotNull(card.getLastAnsweredAt());
        verify(this.cardRepository, times(1)).findCardById(card.getId());
    }
    @Test
    void shouldReturnNullWhenAnswerCard_cardIsNull() {
        UUID cardId = UUID.randomUUID();

        when(this.cardRepository.findCardById(cardId)).thenReturn(null);

        Card card = this.cardService.answerCard(cardId, true);
        assertNull(card);
    }

    @Test
    void shouldNotChangeCategoryIfCardAlreadyInDone() {
        Card card = new Card("Q", "A", "tag");
        card.setCategory("DONE");

        when(cardRepository.findCardById(card.getId())).thenReturn(card);

        cardService.answerCard(card.getId(), true);

        assertEquals("DONE", card.getCategory());
    }

    @Test
    void shouldReturnCardsByTag() {
        List<String> tags = List.of("tag1", "tag2");
        List<Card> expectedCards = List.of(new Card("Q1", "A1", "tag1"), new Card("Q2", "A2", "tag2"));

        when(cardRepository.findCardsByTag(tags)).thenReturn(expectedCards);

        List<Card> result = cardService.getCardsByTag(tags);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(card -> card.getTag().equals("tag1")));
        verify(cardRepository, times(1)).findCardsByTag(tags);
    }

    @Test
    void shouldReturnCardsForTodayQuizz() {
        LocalDate today = LocalDate.now();
        List<Card> expected = List.of(new Card("Q1", "A1", "tag"));

        when(cardRepository.findCardsForQuizzByDate(today)).thenReturn(expected);

        List<Card> result = cardService.getCardsForQuizz(today);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(cardRepository, times(1)).findCardsForQuizzByDate(today);
    }

    @Test
    void shouldReturnNullWhenUpdatingWithNullId() {
        Card result = cardService.updateCard(null, new Card("Q", "A", "tag"));
        assertNull(result);
        verify(cardRepository, never()).save(any());
    }
}

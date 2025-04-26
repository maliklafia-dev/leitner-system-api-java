package com.leitner.leitner_system.infrastructure.adapter.in.rest;

import com.leitner.leitner_system.application.dto.AnswerRequestCardDto;
import com.leitner.leitner_system.application.dto.CardResponseDto;
import com.leitner.leitner_system.application.dto.CardUserData;
import com.leitner.leitner_system.domain.models.Card;
import com.leitner.leitner_system.domain.ports.primary.CardServicePort;
import com.leitner.leitner_system.domain.service.QuizzService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/cards")
@Tag(name = "Cards", description = "Operations related to Leitner cards (create, fetch, answer, etc.)")
public class CardController {

    private final CardServicePort cardService;
    private final QuizzService quizzService;

    public CardController(CardServicePort cardService, QuizzService quizzService) {
        this.cardService = cardService;
        this.quizzService = quizzService;
    }

    @Operation(summary = "Get cards", description = "Returns all cards or cards filtered by tags.")
    @ApiResponse(responseCode = "200", description = "Cards retrieved successfully")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardResponseDto>> getCards(
            @Parameter(description = "Tags to filter cards", example = "Horror")
            @RequestParam(required = false) List<String> tag) {

        List<CardResponseDto> cardDtos = (tag == null || tag.isEmpty())
                ? cardService.getCards().stream().map(CardResponseDto::new).collect(Collectors.toList())
                : cardService.getCardsByTag(tag).stream().map(CardResponseDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(cardDtos);
    }



    @Operation(summary = "Get a card by ID", description = "Fetches a specific card using its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card found"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Card> getCardById(
            @Parameter(description = "The UUID of the card", required = true)
            @PathVariable("id") UUID id) {
        if(id == null) {
             throw new IllegalArgumentException("Id is required");
        }
        Card card = cardService.getCardById(id);
        if(card == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(card);
        }
    }

    @Operation(summary = "Create a new card", description = "Creates a new Leitner card.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Card successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request - question or answer is missing")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CardUserData> createCard(@RequestBody CardUserData cardUserData) {
        if (cardUserData.getQuestion() == null || cardUserData.getAnswer() == null) {
            throw new IllegalArgumentException("Question or answer are required");
        }

        Card card = cardService.createCard(new Card(
                cardUserData.getQuestion(),
                cardUserData.getAnswer(),
                cardUserData.getTag()));

        CardUserData response = new CardUserData(
                card.getId(), card.getQuestion(), card.getAnswer(), card.getTag());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get cards for a quiz", description = "Returns cards scheduled for review today or a specific date.")
    @ApiResponse(responseCode = "200", description = "Cards ready for today's quiz")
    @GetMapping(path = "/quizz", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardUserData>> getCardsForQuizz(
            @Parameter(description = "The date to fetch quiz cards for", example = "2025-04-25")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate targetDate = (date == null) ? LocalDate.now() : date;
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        List<Card> cards = cardService.getCardsForQuizz(targetDate);
        if (!quizzService.hasTakenQuizToday(userId)) {
            quizzService.createQuizz(userId, cards);
        }

        List<CardUserData> response = cards.stream().map(CardUserData::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Answer a card", description = "Submits an answer (correct/incorrect) for a specific card.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Answer successfully recorded"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @PatchMapping(path = "/{cardId}/answer")
    public ResponseEntity<Void> answerCard(
            @Parameter(description = "ID of the card being answered") @PathVariable UUID cardId,
            @RequestBody AnswerRequestCardDto request) {

        Card cardUpdated = cardService.answerCard(cardId, request.isValid());
        return (cardUpdated == null) ? ResponseEntity.notFound().build() : ResponseEntity.noContent().build();
    }
}


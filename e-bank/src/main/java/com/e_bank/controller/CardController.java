package com.e_bank.controller;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.exception.CardIsBlockedException;
import com.e_bank.exception.CardNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les opérations liées aux cartes bancaires.
 */
@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    /**
     * Récupère toutes les cartes associées à un compte par l'identifiant du compte.
     *
     * @param id l'identifiant du compte.
     * @return la liste des cartes associées au compte ou une erreur 404 si la base de données est vide.
     */
    @GetMapping("/get-cards-by-account/{id}")
    public ResponseEntity<?> getAllByAccount(@PathVariable("id") String id) {
        try {
            var cards = cardService.getAllByAccount(Long.valueOf(id));
            return ResponseEntity.ok(cards);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Récupère une carte par son identifiant.
     *
     * @param id l'identifiant de la carte.
     * @return la carte correspondante ou une erreur 404 si la carte n'est pas trouvée.
     */
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            var card = cardService.getById(Long.valueOf(id));
            return ResponseEntity.ok(card);
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Ajoute une nouvelle carte.
     *
     * @param cardDto les détails de la carte à ajouter.
     * @return la carte ajoutée avec un statut 201 (créé).
     */
    @PostMapping("/add")
    public ResponseEntity<CardDto> save(@RequestBody CardDto cardDto) {
        var savedCard = cardService.save(cardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }

    /**
     * Supprime une carte par son identifiant.
     *
     * @param id l'identifiant de la carte à supprimer.
     * @return la carte supprimée ou une erreur 404 si la carte n'est pas trouvée.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            var deletedCard = cardService.delete(Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedCard);
        } catch (CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Active ou désactive une carte.
     *
     * @param cardDto les détails de la carte à modifier.
     * @param id l'identifiant de la carte à activer/désactiver.
     * @return la carte mise à jour ou une erreur 400 si la carte est déjà bloquée ou n'est pas trouvée.
     */
    @PutMapping("/activate-deactivate/{id}")
    public ResponseEntity<?> changeCardStatus(@RequestBody CardStatusDto cardDto, @PathVariable("id") String id) {
        try {
            var updatedCard = cardService.changeCardStatus(cardDto, Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCard);
        } catch (CardIsBlockedException | CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Bloque une carte.
     *
     * @param cardDto les détails de la carte à bloquer.
     * @param id l'identifiant de la carte à bloquer.
     * @return la carte bloquée ou une erreur 400 si la carte est déjà bloquée ou n'est pas trouvée.
     */
    @PutMapping("/block/{id}")
    public ResponseEntity<?> blockCard(@RequestBody CardBlockingDto cardDto, @PathVariable("id") String id) {
        try {
            var blockedCard = cardService.blockCard(cardDto, Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(blockedCard);
        } catch (CardIsBlockedException | CardNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

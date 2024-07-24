package com.e_bank.controller;

import com.e_bank.dto.AccountClosingDto;
import com.e_bank.dto.AccountDto;
import com.e_bank.exception.AccountIsClosedException;
import com.e_bank.exception.AccountNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.exception.InsufficientBalanceException;
import com.e_bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour gérer les opérations liées aux comptes bancaires.
 */
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    /**
     * Récupère un compte par son identifiant.
     *
     * @param id l'identifiant du compte.
     * @return le compte correspondant ou une erreur 404 si le compte n'est pas trouvé.
     */
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            var account = accountService.getById(Long.valueOf(id));
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Récupère tous les comptes d'un utilisateur par l'identifiant de l'utilisateur.
     *
     * @param id l'identifiant de l'utilisateur.
     * @return la liste des comptes de l'utilisateur ou une erreur 404 si la base de données est vide.
     */
    @GetMapping("/get-all-by-user/{id}")
    public ResponseEntity<?> getAllByUser(@PathVariable("id") String id) {
        try {
            var accounts = accountService.getAllByUser(Long.valueOf(id));
            return ResponseEntity.ok(accounts);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Ajoute un nouveau compte.
     *
     * @param accountDto les détails du compte à ajouter.
     * @return le compte ajouté avec un statut 201 (créé).
     */
    @PostMapping("/add")
    public ResponseEntity<AccountDto> save(@RequestBody AccountDto accountDto) {
        var savedAccount = accountService.save(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    /**
     * Met à jour un compte existant par son identifiant.
     *
     * @param accountDto les détails mis à jour du compte.
     * @param id l'identifiant du compte à mettre à jour.
     * @return le compte mis à jour ou une erreur 404 si le compte n'est pas trouvé.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody AccountDto accountDto, @PathVariable("id") String id) {
        try {
            var updatedAccount = accountService.update(accountDto, Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Supprime un compte par son identifiant.
     *
     * @param id l'identifiant du compte à supprimer.
     * @return le compte supprimé ou une erreur 404 si le compte n'est pas trouvé.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            var deletedAccount = accountService.delete(Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedAccount);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Ferme un compte par son identifiant.
     *
     * @param id l'identifiant du compte à fermer.
     * @param accountDto les détails de la fermeture du compte.
     * @return le compte fermé ou une erreur 400 si le solde est insuffisant ou si le compte est déjà fermé,
     *         ou une erreur 404 si le compte n'est pas trouvé.
     */
    @PutMapping("/close-account/{id}")
    public ResponseEntity<?> close(@PathVariable("id") String id, @RequestBody AccountClosingDto accountDto) {
        try {
            var closedAccount = accountService.close(accountDto, Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(closedAccount);
        } catch (InsufficientBalanceException | AccountIsClosedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

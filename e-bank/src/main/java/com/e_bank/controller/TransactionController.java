package com.e_bank.controller;

import com.e_bank.dto.TransactionDto;
import com.e_bank.exception.*;
import com.e_bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    /**
     * Endpoint for internal fund transfer between two accounts.
     *
     * @param transactionDto Details of the transaction.
     * @param creditId       ID of the account to credit.
     * @param debitId        ID of the account to debit.
     * @return ResponseEntity with the list of transactions and status 201 if successful,
     * or status 400 or 404 in case of errors.
     */
    @PostMapping("internal-transaction/{creditId}&{debitId}")
    public ResponseEntity<?> internalTransaction(@RequestBody TransactionDto transactionDto,
                                                 @PathVariable("creditId") String creditId,
                                                 @PathVariable("debitId") String debitId) {
        try {
            var transactions = transactionService.internalTransaction(transactionDto, Long.valueOf(creditId), Long.valueOf(debitId));
            return ResponseEntity.status(HttpStatus.CREATED).body(transactions);
        } catch (InsufficientBalanceException | AccountIsClosedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint for external fund transfer from an account to a beneficiary.
     *
     * @param transactionDto Details of the transaction.
     * @param accountId      ID of the account from which funds are transferred.
     * @param beneficiaryId  ID of the beneficiary account.
     * @return ResponseEntity with the transaction details and status 201 if successful,
     * or status 400 or 404 in case of errors.
     */
    @PostMapping("external-transaction/{accountId}&{beneficiaryId}")
    public ResponseEntity<?> externalTransaction(@RequestBody TransactionDto transactionDto,
                                                 @PathVariable("accountId") String accountId,
                                                 @PathVariable("beneficiaryId") String beneficiaryId) {
        try {
            var transaction = transactionService.externalTransaction(transactionDto, Long.valueOf(accountId), Long.valueOf(beneficiaryId));
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (InsufficientBalanceException | AccountIsClosedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException | BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint for fund transfer using a card to a beneficiary.
     *
     * @param transactionDto Details of the transaction.
     * @param cardId         ID of the card from which funds are transferred.
     * @param beneficiaryId  ID of the beneficiary account.
     * @return ResponseEntity with the card transaction details and status 201 if successful,
     * or status 400 or 404 in case of errors.
     */
    @PostMapping("card-transaction/{cardId}&{beneficiaryId}")
    public ResponseEntity<?> cardTransaction(@RequestBody TransactionDto transactionDto,
                                             @PathVariable("cardId") String cardId,
                                             @PathVariable("beneficiaryId") String beneficiaryId) {
        try {
            var card = transactionService.cardTransaction(transactionDto, Long.valueOf(cardId), Long.valueOf(beneficiaryId));
            return ResponseEntity.status(HttpStatus.CREATED).body(card);
        } catch (InsufficientBalanceException | AccountIsClosedException | CardIsBlockedException | CardDeactivatedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (AccountNotFoundException | BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves all transactions associated with a specific account.
     *
     * @param id ID of the account.
     * @return ResponseEntity with the list of transactions or status 404 if no transactions found.
     */
    @GetMapping("get-transactions-by-account/{id}")
    public ResponseEntity<?> getAllByAccount(@PathVariable("id") String id) {
        try {
            var transactions = transactionService.getAllByAccount(Long.valueOf(id));
            return ResponseEntity.ok(transactions);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id ID of the transaction.
     * @return ResponseEntity with the transaction details or status 404 if transaction not found.
     */
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            var transaction = transactionService.getById(Long.valueOf(id));
            return ResponseEntity.ok(transaction);
        } catch (TransactionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

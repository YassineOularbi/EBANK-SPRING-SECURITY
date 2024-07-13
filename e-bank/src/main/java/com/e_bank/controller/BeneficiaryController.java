package com.e_bank.controller;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.exception.BeneficiaryNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.service.BeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beneficiary")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;

    @GetMapping("/get-all-beneficiary-by-account/{id}")
    public ResponseEntity<?> getAllByAccount(@PathVariable("id") String id) {
        try {
            var beneficiaries = beneficiaryService.getAllByAccount(Long.valueOf(id));
            return ResponseEntity.ok(beneficiaries);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try {
            var beneficiary = beneficiaryService.getById(Long.valueOf(id));
            return ResponseEntity.ok(beneficiary);
        } catch (BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<BeneficiaryDto> save(@RequestBody BeneficiaryDto beneficiaryDto) {
        var savedBeneficiary = beneficiaryService.save(beneficiaryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBeneficiary);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody BeneficiaryDto beneficiaryDto, @PathVariable("id") String id) {
        try {
            var updatedBeneficiary = beneficiaryService.update(beneficiaryDto, Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedBeneficiary);
        } catch (BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        try {
            var deletedBeneficiary = beneficiaryService.delete(Long.valueOf(id));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedBeneficiary);
        } catch (BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

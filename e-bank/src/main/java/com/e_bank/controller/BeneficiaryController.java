package com.e_bank.controller;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.exception.BeneficiaryNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beneficiary")
public class BeneficiaryController {
    @Autowired
    private BeneficiaryService beneficiaryService;

    @GetMapping("/get-all-beneficiary-by-account/{id}")
    public ResponseEntity<?> getAllByAccount(@PathVariable Long id) {
        try {
            var beneficiaries = beneficiaryService.getAllByAccount(id);
            return ResponseEntity.ok(beneficiaries);
        } catch (DatabaseEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            var beneficiary = beneficiaryService.getById(id);
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
    public ResponseEntity<?> update(@RequestBody BeneficiaryDto beneficiaryDto, @PathVariable Long id) {
        try {
            var updatedBeneficiary = beneficiaryService.update(beneficiaryDto, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedBeneficiary);
        } catch (BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            var deletedBeneficiary = beneficiaryService.delete(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(deletedBeneficiary);
        } catch (BeneficiaryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

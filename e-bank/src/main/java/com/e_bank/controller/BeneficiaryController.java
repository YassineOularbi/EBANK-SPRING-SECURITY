package com.e_bank.controller;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("beneficiary")
public class BeneficiaryController {
    @Autowired
    private BeneficiaryService beneficiaryService;
    @GetMapping("get-all-beneficiary-by-account/{id}")
    public ResponseEntity<List<BeneficiaryDto>> getAllByAccount(@PathVariable Long id){
        return ResponseEntity.ok(beneficiaryService.getAllByAccount(id));
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<BeneficiaryDto> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(beneficiaryService.getById(id));
    }
    @PostMapping("add")
    public ResponseEntity<BeneficiaryDto> save(@RequestBody BeneficiaryDto beneficiaryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(beneficiaryService.save(beneficiaryDto));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<BeneficiaryDto> update(@RequestBody BeneficiaryDto beneficiaryDto, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(beneficiaryService.update(beneficiaryDto, id));
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<BeneficiaryDto> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(beneficiaryService.delete(id));
    }
}

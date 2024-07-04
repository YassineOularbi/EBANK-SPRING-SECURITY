package com.e_bank.service;

import com.e_bank.exception.BeneficiaryNotFoundException;
import com.e_bank.model.Beneficiary;
import com.e_bank.repository.BeneficiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryService {
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;
    public List<Beneficiary> getAll(){
        return beneficiaryRepository.findAll();
    }
    public Beneficiary save(Beneficiary beneficiary){
        return beneficiaryRepository.save(beneficiary);
    }
    public Beneficiary getById(Long id){
        return beneficiaryRepository.findById(id).orElseThrow(BeneficiaryNotFoundException::new);
    }
    public Beneficiary delete(Long id){
        beneficiaryRepository.deleteById(id);
        return beneficiaryRepository.findById(id).orElseThrow(BeneficiaryNotFoundException::new);
    }
}

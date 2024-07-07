package com.e_bank.service;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.exception.BeneficiaryNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.mapper.BeneficiaryMapper;
import com.e_bank.model.Beneficiary;
import com.e_bank.repository.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private BeneficiaryMapper beneficiaryMapper;

    //    public List<Beneficiary> getAll(){
//        return beneficiaryRepository.findAll();
//    }

    public List<BeneficiaryDto> getAllByAccount(Long id) {
        var beneficiaries = beneficiaryRepository.findAllByAccount_Id(id);
        if (beneficiaries.isEmpty()) {
            throw new DatabaseEmptyException();
        }
        return beneficiaryMapper.toDtos(beneficiaries);
    }

    public BeneficiaryDto save(BeneficiaryDto beneficiaryDto) {
        var beneficiary = beneficiaryMapper.toBeneficiary(beneficiaryDto);
        return beneficiaryMapper.toDto(beneficiaryRepository.save(beneficiary));
    }

    public BeneficiaryDto update(BeneficiaryDto beneficiaryDto, Long id) {
        var beneficiary = beneficiaryRepository.findById(id).orElseThrow(BeneficiaryNotFoundException::new);
        var beneficiaryUpdated = beneficiaryMapper.updateBeneficiaryFromDto(beneficiaryDto, beneficiary);
        return beneficiaryMapper.toDto(beneficiaryRepository.save(beneficiaryUpdated));
    }

    public BeneficiaryDto getById(Long id) {
        var beneficiary = beneficiaryRepository.findById(id).orElseThrow(BeneficiaryNotFoundException::new);
        return beneficiaryMapper.toDto(beneficiary);
    }

    public BeneficiaryDto delete(Long id) {
        var beneficiary = beneficiaryRepository.findById(id).orElseThrow(BeneficiaryNotFoundException::new);
        beneficiaryRepository.delete(beneficiary);
        return beneficiaryMapper.toDto(beneficiary);
    }
}

package com.e_bank.service;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.exception.*;
import com.e_bank.mapper.BeneficiaryMapper;
import com.e_bank.repository.BeneficiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing beneficiaries in the E-Bank application.
 */
@Service
@RequiredArgsConstructor
public class BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;

    private final BeneficiaryMapper beneficiaryMapper;

    /**
     * Retrieves all beneficiaries associated with a specific account.
     *
     * @param id ID of the account
     * @return List of BeneficiaryDto
     * @throws DatabaseEmptyException if no beneficiaries are found for the account
     */
    public List<BeneficiaryDto> getAllByAccount(Long id) {
        var beneficiaries = beneficiaryRepository.findAllByAccount_Id(id);
        if (beneficiaries.isEmpty()) {
            throw new DatabaseEmptyException();
        }
        return beneficiaryMapper.toDtos(beneficiaries);
    }

    /**
     * Saves a new beneficiary.
     *
     * @param beneficiaryDto Data of the beneficiary to be saved
     * @return Saved BeneficiaryDto
     */
    public BeneficiaryDto save(BeneficiaryDto beneficiaryDto) {
        var beneficiary = beneficiaryMapper.toBeneficiary(beneficiaryDto);
        return beneficiaryMapper.toDto(beneficiaryRepository.save(beneficiary));
    }

    /**
     * Updates an existing beneficiary.
     *
     * @param beneficiaryDto Data of the beneficiary to update
     * @param id ID of the beneficiary to update
     * @return Updated BeneficiaryDto
     * @throws BeneficiaryNotFoundException if the beneficiary with given ID is not found
     */
    public BeneficiaryDto update(BeneficiaryDto beneficiaryDto, Long id) {
        var beneficiary = beneficiaryRepository.findById(id).orElseThrow(BeneficiaryNotFoundException::new);
        var beneficiaryUpdated = beneficiaryMapper.updateBeneficiaryFromDto(beneficiaryDto, beneficiary);
        return beneficiaryMapper.toDto(beneficiaryRepository.save(beneficiaryUpdated));
    }

    /**
     * Retrieves a beneficiary by ID.
     *
     * @param id ID of the beneficiary to retrieve
     * @return BeneficiaryDto
     * @throws BeneficiaryNotFoundException if the beneficiary with given ID is not found
     */
    public BeneficiaryDto getById(Long id) {
        var beneficiary = beneficiaryRepository.findById(id).orElseThrow(BeneficiaryNotFoundException::new);
        return beneficiaryMapper.toDto(beneficiary);
    }

    /**
     * Deletes a beneficiary by ID.
     *
     * @param id ID of the beneficiary to delete
     * @return Deleted BeneficiaryDto
     * @throws BeneficiaryNotFoundException if the beneficiary with given ID is not found
     */
    public BeneficiaryDto delete(Long id) {
        var beneficiary = beneficiaryRepository.findById(id).orElseThrow(BeneficiaryNotFoundException::new);
        beneficiaryRepository.delete(beneficiary);
        return beneficiaryMapper.toDto(beneficiary);
    }
}

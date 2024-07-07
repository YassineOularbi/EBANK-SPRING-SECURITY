package com.e_bank;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.exception.BeneficiaryNotFoundException;
import com.e_bank.exception.DatabaseEmptyException;
import com.e_bank.mapper.BeneficiaryMapper;
import com.e_bank.model.Beneficiary;
import com.e_bank.repository.BeneficiaryRepository;
import com.e_bank.service.BeneficiaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the BeneficiaryService.
 */
public class BeneficiaryServiceTests {

    @Mock
    private BeneficiaryRepository beneficiaryRepository;

    @Mock
    private BeneficiaryMapper beneficiaryMapper;

    @InjectMocks
    private BeneficiaryService beneficiaryService;

    /**
     * Sets up the mock objects before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the success scenario of retrieving all beneficiaries by account ID.
     */
    @Test
    public void testGetAllByAccount_Success() {
        when(beneficiaryRepository.findAllByAccount_Id(1L)).thenReturn(Collections.singletonList(new Beneficiary()));
        when(beneficiaryMapper.toDtos(anyList())).thenReturn(Collections.singletonList(new BeneficiaryDto()));

        var result = beneficiaryService.getAllByAccount(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    /**
     * Tests the scenario where the database is empty when retrieving all beneficiaries by account ID.
     */
    @Test
    public void testGetAllByAccount_EmptyDatabase() {
        when(beneficiaryRepository.findAllByAccount_Id(1L)).thenReturn(Collections.emptyList());

        assertThrows(DatabaseEmptyException.class, () -> beneficiaryService.getAllByAccount(1L));
    }

    /**
     * Tests the success scenario of saving a beneficiary.
     */
    @Test
    public void testSaveBeneficiary_Success() {
        BeneficiaryDto beneficiaryDto = new BeneficiaryDto();
        Beneficiary beneficiary = new Beneficiary();

        when(beneficiaryMapper.toBeneficiary(beneficiaryDto)).thenReturn(beneficiary);
        when(beneficiaryMapper.toDto(beneficiary)).thenReturn(beneficiaryDto);

        when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);

        var savedBeneficiary = beneficiaryService.save(beneficiaryDto);

        assertNotNull(savedBeneficiary);
    }

    /**
     * Tests the success scenario of updating a beneficiary.
     */
    @Test
    public void testUpdateBeneficiary_Success() {
        Long beneficiaryId = 1L;
        BeneficiaryDto beneficiaryDto = new BeneficiaryDto();
        Beneficiary beneficiary = new Beneficiary();

        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(beneficiary));
        when(beneficiaryMapper.updateBeneficiaryFromDto(beneficiaryDto, beneficiary)).thenReturn(beneficiary);
        when(beneficiaryMapper.toDto(beneficiary)).thenReturn(beneficiaryDto);

        var updatedBeneficiary = beneficiaryService.update(beneficiaryDto, beneficiaryId);

        assertNotNull(updatedBeneficiary);
    }

    /**
     * Tests the scenario where a beneficiary is not found by ID during update.
     */
    @Test
    public void testUpdateBeneficiary_NotFound() {
        Long beneficiaryId = 1L;
        BeneficiaryDto beneficiaryDto = new BeneficiaryDto();

        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.empty());

        assertThrows(BeneficiaryNotFoundException.class, () -> beneficiaryService.update(beneficiaryDto, beneficiaryId));
    }

    /**
     * Tests the success scenario of retrieving a beneficiary by ID.
     */
    @Test
    public void testGetBeneficiaryById_Success() {
        Long beneficiaryId = 1L;

        Beneficiary beneficiary = new Beneficiary();
        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(beneficiary));
        when(beneficiaryMapper.toDto(beneficiary)).thenReturn(new BeneficiaryDto());

        var fetchedBeneficiary = beneficiaryService.getById(beneficiaryId);

        assertNotNull(fetchedBeneficiary);
    }

    /**
     * Tests the scenario where a beneficiary is not found by ID during retrieval.
     */
    @Test
    public void testGetBeneficiaryById_NotFound() {
        Long beneficiaryId = 1L;

        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.empty());

        assertThrows(BeneficiaryNotFoundException.class, () -> beneficiaryService.getById(beneficiaryId));
    }

    /**
     * Tests the success scenario of deleting a beneficiary.
     */
    @Test
    public void testDeleteBeneficiary_Success() {
        Long beneficiaryId = 1L;

        Beneficiary beneficiary = new Beneficiary();
        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.of(beneficiary));
        when(beneficiaryMapper.toDto(beneficiary)).thenReturn(new BeneficiaryDto());

        var deletedBeneficiary = beneficiaryService.delete(beneficiaryId);

        assertNotNull(deletedBeneficiary);
    }

    /**
     * Tests the scenario where a beneficiary is not found by ID during deletion.
     */
    @Test
    public void testDeleteBeneficiary_NotFound() {
        Long beneficiaryId = 1L;

        when(beneficiaryRepository.findById(beneficiaryId)).thenReturn(Optional.empty());

        assertThrows(BeneficiaryNotFoundException.class, () -> beneficiaryService.delete(beneficiaryId));
    }
}

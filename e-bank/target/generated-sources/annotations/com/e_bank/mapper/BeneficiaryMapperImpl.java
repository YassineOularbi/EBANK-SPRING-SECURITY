package com.e_bank.mapper;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.model.Account;
import com.e_bank.model.Beneficiary;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T15:54:06+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class BeneficiaryMapperImpl implements BeneficiaryMapper {

    @Override
    public Beneficiary toBeneficiary(BeneficiaryDto beneficiaryDto) {
        if ( beneficiaryDto == null ) {
            return null;
        }

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setAccount( beneficiaryDtoToAccount( beneficiaryDto ) );
        beneficiary.setName( beneficiaryDto.getName() );
        beneficiary.setIBAN( beneficiaryDto.getIBAN() );
        beneficiary.setBank( beneficiaryDto.getBank() );

        return beneficiary;
    }

    @Override
    public BeneficiaryDto toDto(Beneficiary beneficiary) {
        if ( beneficiary == null ) {
            return null;
        }

        BeneficiaryDto beneficiaryDto = new BeneficiaryDto();

        beneficiaryDto.setAccount_id( beneficiaryAccountId( beneficiary ) );
        beneficiaryDto.setName( beneficiary.getName() );
        beneficiaryDto.setIBAN( beneficiary.getIBAN() );
        beneficiaryDto.setBank( beneficiary.getBank() );

        return beneficiaryDto;
    }

    @Override
    public List<Beneficiary> toBeneficiarys(List<BeneficiaryDto> beneficiaryDtos) {
        if ( beneficiaryDtos == null ) {
            return null;
        }

        List<Beneficiary> list = new ArrayList<Beneficiary>( beneficiaryDtos.size() );
        for ( BeneficiaryDto beneficiaryDto : beneficiaryDtos ) {
            list.add( toBeneficiary( beneficiaryDto ) );
        }

        return list;
    }

    @Override
    public List<BeneficiaryDto> toDtos(List<Beneficiary> beneficiaries) {
        if ( beneficiaries == null ) {
            return null;
        }

        List<BeneficiaryDto> list = new ArrayList<BeneficiaryDto>( beneficiaries.size() );
        for ( Beneficiary beneficiary : beneficiaries ) {
            list.add( toDto( beneficiary ) );
        }

        return list;
    }

    @Override
    public Beneficiary updateBeneficiaryFromDto(BeneficiaryDto beneficiaryDto, Beneficiary beneficiary) {
        if ( beneficiaryDto == null ) {
            return beneficiary;
        }

        if ( beneficiary.getAccount() == null ) {
            beneficiary.setAccount( new Account() );
        }
        beneficiaryDtoToAccount1( beneficiaryDto, beneficiary.getAccount() );
        beneficiary.setName( beneficiaryDto.getName() );
        beneficiary.setIBAN( beneficiaryDto.getIBAN() );
        beneficiary.setBank( beneficiaryDto.getBank() );

        return beneficiary;
    }

    protected Account beneficiaryDtoToAccount(BeneficiaryDto beneficiaryDto) {
        if ( beneficiaryDto == null ) {
            return null;
        }

        Account account = new Account();

        account.setId( beneficiaryDto.getAccount_id() );

        return account;
    }

    private Long beneficiaryAccountId(Beneficiary beneficiary) {
        if ( beneficiary == null ) {
            return null;
        }
        Account account = beneficiary.getAccount();
        if ( account == null ) {
            return null;
        }
        Long id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected void beneficiaryDtoToAccount1(BeneficiaryDto beneficiaryDto, Account mappingTarget) {
        if ( beneficiaryDto == null ) {
            return;
        }

        mappingTarget.setId( beneficiaryDto.getAccount_id() );
    }
}
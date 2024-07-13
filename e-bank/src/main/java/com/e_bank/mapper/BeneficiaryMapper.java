package com.e_bank.mapper;

import com.e_bank.dto.BeneficiaryDto;
import com.e_bank.model.Beneficiary;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeneficiaryMapper {
    @Mapping(target = "account.id", source = "account_id")
    Beneficiary toBeneficiary(BeneficiaryDto beneficiaryDto);
    @Mapping(target = "account_id", source = "account.id")
    BeneficiaryDto toDto(Beneficiary beneficiary);
    @Mapping(target = "account.id", source = "account_id")
    List<Beneficiary> toBeneficiarys(List<BeneficiaryDto> beneficiaryDtos);
    @Mapping(target = "account_id", source = "account.id")
    List<BeneficiaryDto> toDtos(List<Beneficiary> beneficiaries);
    @Mapping(target = "account.id", source = "account_id")
    Beneficiary updateBeneficiaryFromDto(BeneficiaryDto beneficiaryDto, @MappingTarget Beneficiary beneficiary);
}

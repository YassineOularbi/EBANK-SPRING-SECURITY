package com.e_bank.mapper;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mapping(target = "account.id", source = "account_id")
    Card toCard(CardDto cardDto);
    @Mapping(target = "account_id", source = "account.id")
    CardDto toDto(Card card);
    @Mapping(target = "account.id", source = "account_id")
    List<Card> toCards(List<CardDto> cardDtos);
    @Mapping(target = "account_id", source = "account.id")
    List<CardDto> toDtos(List<Card> cards);
    Card changeCardStatus(CardStatusDto cardDto, @MappingTarget Card card);
    Card blockCard(CardBlockingDto cardDto, @MappingTarget Card card);
}

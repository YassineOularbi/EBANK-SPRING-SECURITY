package com.e_bank.mapper;

import com.e_bank.dto.CardBlockingDto;
import com.e_bank.dto.CardDto;
import com.e_bank.dto.CardStatusDto;
import com.e_bank.model.Account;
import com.e_bank.model.Card;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-13T18:17:46+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CardMapperImpl implements CardMapper {

    @Override
    public Card toCard(CardDto cardDto) {
        if ( cardDto == null ) {
            return null;
        }

        Card.CardBuilder card = Card.builder();

        card.account( cardDtoToAccount( cardDto ) );
        card.type( cardDto.getType() );
        card.network( cardDto.getNetwork() );
        card.tier( cardDto.getTier() );

        return card.build();
    }

    @Override
    public CardDto toDto(Card card) {
        if ( card == null ) {
            return null;
        }

        CardDto.CardDtoBuilder cardDto = CardDto.builder();

        cardDto.account_id( cardAccountId( card ) );
        cardDto.type( card.getType() );
        cardDto.network( card.getNetwork() );
        cardDto.tier( card.getTier() );

        return cardDto.build();
    }

    @Override
    public List<Card> toCards(List<CardDto> cardDtos) {
        if ( cardDtos == null ) {
            return null;
        }

        List<Card> list = new ArrayList<Card>( cardDtos.size() );
        for ( CardDto cardDto : cardDtos ) {
            list.add( toCard( cardDto ) );
        }

        return list;
    }

    @Override
    public List<CardDto> toDtos(List<Card> cards) {
        if ( cards == null ) {
            return null;
        }

        List<CardDto> list = new ArrayList<CardDto>( cards.size() );
        for ( Card card : cards ) {
            list.add( toDto( card ) );
        }

        return list;
    }

    @Override
    public Card changeCardStatus(CardStatusDto cardDto, Card card) {
        if ( cardDto == null ) {
            return card;
        }

        card.setIsActivated( cardDto.getIsActivated() );

        return card;
    }

    @Override
    public Card blockCard(CardBlockingDto cardDto, Card card) {
        if ( cardDto == null ) {
            return card;
        }

        card.setBlockingReason( cardDto.getBlockingReason() );

        return card;
    }

    protected Account cardDtoToAccount(CardDto cardDto) {
        if ( cardDto == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.id( cardDto.getAccount_id() );

        return account.build();
    }

    private Long cardAccountId(Card card) {
        if ( card == null ) {
            return null;
        }
        Account account = card.getAccount();
        if ( account == null ) {
            return null;
        }
        Long id = account.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

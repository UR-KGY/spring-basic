package com.gamebasic.ranking.client.dto;

import lombok.Getter;

@Getter
public class Card {
    private final CardType cardType;
    private final int acquiredFloor;

    public Card(CardType cardType, int acquiredFloor) {
        this.cardType = cardType;
        this.acquiredFloor = acquiredFloor;
    }
}

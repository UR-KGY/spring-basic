package com.gamebasic.ranking.client.dto;

import lombok.Getter;

@Getter
public class Card {
    private final String cardType;
    private final int acquiredFloor;

    public Card(String cardType, int acquiredFloor) {
        this.cardType = cardType;
        this.acquiredFloor = acquiredFloor;
    }
}

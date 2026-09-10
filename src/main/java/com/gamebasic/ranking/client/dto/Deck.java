package com.gamebasic.ranking.client.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class Deck {
    private final int size;
    private final List<Card> cards;

    public Deck(int size, List<Card> cards) {
        this.size = size;
        this.cards = cards;
    }
}

package com.gamebasic.runcard.dto.projection;

import lombok.Getter;

@Getter
public class DeckCount {
    private final Long gameId;
    private final Long deckSize;

    public DeckCount(Long gameId, Long deckSize) {
        this.gameId = gameId;
        this.deckSize = deckSize;
    }
}

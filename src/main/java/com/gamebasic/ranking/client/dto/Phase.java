package com.gamebasic.ranking.client.dto;

import lombok.Getter;

@Getter
public class Phase {
    private final String phase;
    private final int turns;
    private final int damageTaken;

    public Phase(String phase, int turns, int damageTaken) {
        this.phase = phase;
        this.turns = turns;
        this.damageTaken = damageTaken;
    }
}

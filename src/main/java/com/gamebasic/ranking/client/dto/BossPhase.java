package com.gamebasic.ranking.client.dto;

import lombok.Getter;

@Getter
public class BossPhase {
    private final Phase phase;
    private final int turns;
    private final int damageTaken;

    public BossPhase(Phase phase, int turns, int damageTaken) {
        this.phase = phase;
        this.turns = turns;
        this.damageTaken = damageTaken;
    }
}

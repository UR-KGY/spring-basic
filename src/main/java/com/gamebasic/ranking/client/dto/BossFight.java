package com.gamebasic.ranking.client.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BossFight {
    private final List<Phase> phases;
    private final String finishingCard;
    private final int totalTurns;

    public BossFight(List<Phase> phases, String finishingCard, int totalTurns) {
        this.phases = phases;
        this.finishingCard = finishingCard;
        this.totalTurns = totalTurns;
    }
}

package com.gamebasic.ranking.client.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class Floor {
    private final int floor;
    private final String enemy;
    private final int turns;
    private final int hpAfter;
    private final List<Reward> rewards;

    public Floor(int floor, String enemy, int turns, int hpAfter, List<Reward> rewards) {
        this.floor = floor;
        this.enemy = enemy;
        this.turns = turns;
        this.hpAfter = hpAfter;
        this.rewards = rewards;
    }
}

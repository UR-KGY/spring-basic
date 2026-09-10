package com.gamebasic.ranking.client.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class Run {
    private final String seed;
    private final String status;
    private final int clearedFloor;
    private final int durationSeconds;
    private final int finalHp;
    private final List<Floor> floors;

    public Run(String seed, String status, int clearedFloor, int durationSeconds, int finalHp, List<Floor> floors) {
        this.seed = seed;
        this.status = status;
        this.clearedFloor = clearedFloor;
        this.durationSeconds = durationSeconds;
        this.finalHp = finalHp;
        this.floors = floors;
    }
}

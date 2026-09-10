package com.gamebasic.ranking.client.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class Reward {
    private final List<String> offered;
    private final String picked;

    public Reward(List<String> offered, String picked) {
        this.offered = offered;
        this.picked = picked;
    }
}

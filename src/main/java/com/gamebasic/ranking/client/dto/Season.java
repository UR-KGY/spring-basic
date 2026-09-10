package com.gamebasic.ranking.client.dto;

import lombok.Getter;

@Getter
public class Season {
    private final String id;
    private final String name;
    private final String startsAt;
    private final String endsAt;

    public Season(String id, String name, String startsAt, String endsAt) {
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }
}

package com.gamebasic.ranking.client.dto;

import java.util.List;

public class Player {
    private final String id;
    private final String name;
    private final String region;
    private final List<String> tags;

    public Player(String id, String name, String region, List<String> tags) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.tags = tags;
    }
}

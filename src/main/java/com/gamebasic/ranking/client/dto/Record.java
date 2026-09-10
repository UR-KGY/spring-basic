package com.gamebasic.ranking.client.dto;

import lombok.Getter;

@Getter
public class Record {
    private final int id;
    private final String submittedAt;
    private final Client client;
    private final Player player;
    private final Run run;
    private final BossFight bossFight;
    private final Deck deck;

    public Record(int id, String submittedAt, Client client, Player player, Run run, BossFight bossFight, Deck deck) {
        this.id = id;
        this.submittedAt = submittedAt;
        this.client = client;
        this.player = player;
        this.run = run;
        this.bossFight = bossFight;
        this.deck = deck;
    }
}

package com.gamebasic.ranking.client.dto;

import lombok.Getter;

@Getter
public class Meta {
    private final Season season;
    private final String generatedAt;
    private final int schemaVersion;
    private final int totalRecords;

    public Meta(Season season, String generatedAt, int schemaVersion, int totalRecords) {
        this.season = season;
        this.generatedAt = generatedAt;
        this.schemaVersion = schemaVersion;
        this.totalRecords = totalRecords;
    }
}

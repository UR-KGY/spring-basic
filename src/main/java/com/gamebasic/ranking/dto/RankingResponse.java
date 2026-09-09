package com.gamebasic.ranking.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RankingResponse {
    private String season;
    private int totalRecords;
    private int excludedCount;
    private List<Entry> entries;

    public RankingResponse(String season, int totalRecords, int excludedCount, List<Entry> entries) {
        this.season = season;
        this.totalRecords = totalRecords;
        this.excludedCount = excludedCount;
        this.entries = entries;
    }
}

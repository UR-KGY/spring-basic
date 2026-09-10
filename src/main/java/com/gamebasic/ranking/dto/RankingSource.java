package com.gamebasic.ranking.dto;

import com.gamebasic.ranking.client.dto.Meta;
import com.gamebasic.ranking.client.dto.Record;
import lombok.Getter;

import java.util.List;

@Getter
public class RankingSource {
    private final Meta meta;
    private final List<Record> records;

    public RankingSource(Meta meta, List<Record> records) {
        this.meta = meta;
        this.records = records;
    }

}

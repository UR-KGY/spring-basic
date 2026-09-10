package com.gamebasic.ranking.dto;

import com.gamebasic.ranking.client.dto.Record;
import lombok.Getter;

import java.util.List;

@Getter
public class ExcludeRecordDto {
    private final int excludeCount;
    private final List<Record> records;

    public ExcludeRecordDto(int excludeCount, List<Record> records) {
        this.excludeCount = excludeCount;
        this.records = records;
    }
}

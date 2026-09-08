package com.gamebasic.runcard.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RunCardRequest {
    // TODO (Lv 5): API 명세의 카드 필드 제약을 Bean Validation 어노테이션으로 붙이세요.
    @NotEmpty
    private String cardType;

    @Max(10)
    @Min(0)
    private Integer acquiredFloor;

    public RunCardRequest(String cardType, Integer acquiredFloor) {
        this.cardType = cardType;
        this.acquiredFloor = acquiredFloor;
    }
}

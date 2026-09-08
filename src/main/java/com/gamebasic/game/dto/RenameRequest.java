package com.gamebasic.game.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RenameRequest {
    @Size(min = 2,max =12)
    private String playerName;

    public RenameRequest(String playerName) {
        this.playerName = playerName;
    }
}

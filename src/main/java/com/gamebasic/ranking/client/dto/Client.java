package com.gamebasic.ranking.client.dto;

import lombok.Getter;

@Getter
public class Client {
    private final String version;
    private final String platform;
    private final String locale;

    public Client(String version, String platform, String locale) {
        this.version = version;
        this.platform = platform;
        this.locale = locale;
    }
}

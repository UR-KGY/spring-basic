package com.gamebasic.ranking.client;

import org.springframework.web.client.RestClient;

public class RankingClient {
    //api 요청을 보낼 url?
    private static final String SOURCE_URL = "https://f-api.github.io/game-spring-api-docs/basic/rankings.json";

    private final RestClient restClient = RestClient.create();

    public RankingSource fetch(){
        return restClient.get()
                .uri(SOURCE_URL)
                .retrieve()
                .body(RankingSource.class);
    }
}

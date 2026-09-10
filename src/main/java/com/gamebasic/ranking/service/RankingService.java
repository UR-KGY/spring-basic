package com.gamebasic.ranking.service;

import com.gamebasic.ranking.client.RankingClient;
import com.gamebasic.ranking.dto.RankingResponse;
import com.gamebasic.ranking.dto.RankingSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RankingService {
    private final RankingClient rankingClient;

    public RankingResponse getRankings() {
        RankingSource rankingSource = rankingClient.fetch();

//        RankingResponse response = new RankingResponse(
//                rankingSource.getMeta().getSeason().getName(),
//        )

        return null;
    }

}

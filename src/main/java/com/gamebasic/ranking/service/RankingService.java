package com.gamebasic.ranking.service;

import com.gamebasic.ranking.client.RankingClient;
import com.gamebasic.ranking.client.dto.BossPhase;
import com.gamebasic.ranking.client.dto.CardType;
import com.gamebasic.ranking.client.dto.Phase;
import com.gamebasic.ranking.client.dto.Record;
import com.gamebasic.ranking.dto.Entry;
import com.gamebasic.ranking.dto.ExcludeRecordDto;
import com.gamebasic.ranking.dto.RankingResponse;
import com.gamebasic.ranking.dto.RankingSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


@RequiredArgsConstructor
@Service
public class RankingService {
    private final RankingClient rankingClient;

    public RankingResponse getRankings() {
        RankingSource rankingSource = rankingClient.fetch();

        String name = rankingSource.getMeta().getSeason().getName();
        int totalRecords = rankingSource.getMeta().getTotalRecords();


        List<Record> records = selectClearRecords(rankingSource.getRecords());
        ExcludeRecordDto dto = excludeWrongRecords(records);

        RankingResponse response = new RankingResponse(
                name,
                totalRecords,
                dto.getExcludeCount(),
                null
                //todo : entry 배열을 채워놓기


        );

        return null;
    }

    //run.status 가 CLEARED 이고 run.clearedFloor 가 10인 기록만 반환
    private List<Record> selectClearRecords(List<Record> records) {
        return records.stream()
                .filter(record -> record.getRun().getStatus().equals("Cleared")
                        && record.getRun().getClearedFloor() == 10)
                .toList();
    }

    private ExcludeRecordDto excludeWrongRecords(List<Record> records) {
        int before = records.size();
        List<Record> result = records.stream()
                .filter(isValidRecord).toList();

        int after = records.size();

        return new ExcludeRecordDto(
                before-after,
                result
        );
    }


    //todo : 한꺼번에 필터링 후 전 후 비교를 하려다보니 복잡해졌을지도 모른다. 일단 기능 구현에 집중하고 시간 있을 때 리팩토링

    //run.durationSeconds가 층당 30초 이상, 즉 run.clearedFloor × 30 이상
    Predicate<Record> clearTimeOk = r -> r.getRun().getDurationSeconds() >= r.getRun().getClearedFloor() * 30;
    //run.finalHp가 1 이상 99 이하
    Predicate<Record> hpOk = r -> r.getRun().getFinalHp() >= 1 && r.getRun().getFinalHp() <= 99;
    //deck.cards가 9장 이상 20장 이하이고, deck.size가 deck.cards의 실제 개수와 같음
    Predicate<Record> deckCardsOk = r -> r.getDeck().getCards().size() >= 9 && r.getDeck().getCards().size() <= 20 && r.getDeck().getSize() == r.getDeck().getCards().size();
    //카드가 enum 목록안에 존재하는 카드인지를 확인
    Predicate<Record> cardTypeOk = r -> r.getDeck().getCards().stream().allMatch(card -> Arrays.stream(CardType.values()).anyMatch(cardType -> cardType == card.getCardType()));
    //카드를 휙득한 층이 0이상 9이하인지
    Predicate<Record> acquiredFloorOk = r -> r.getDeck().getCards().stream().allMatch(card -> card.getAcquiredFloor() >=0 && card.getAcquiredFloor() <=9);
    //보스 페이즈가 순서대로 THRONE,UNBOUND,ECLIPSE 순서인지 와 각 turns 가 1이상이고  totalTurns 가 세 개의 turns 의 합과 같은지
    Predicate<Record> bossPhaseOk = r-> r.getBossFight().getPhases().stream().map(BossPhase::getPhase).toList().equals(List.of(Phase.THRONE,Phase.UNBOUND,Phase.ECLIPSE))
            && r.getBossFight().getPhases().stream().allMatch(p -> p.getTurns() >=1) && r.getBossFight().getTotalTurns() == r.getBossFight().getPhases().stream().mapToInt(BossPhase::getTurns).sum();
    //bossFight.finishingCard가 그 기록의 deck.cards에 있는 카드 타입
    Predicate<Record> finishingCardOk = r-> r.getDeck().getCards().stream().anyMatch(card -> card.getCardType().toString().equals(r.getBossFight().getFinishingCard()));


    //종합적인 제외 조건
    Predicate<Record> isValidRecord = clearTimeOk.and(hpOk).and(deckCardsOk).and(cardTypeOk).and(acquiredFloorOk).and(bossPhaseOk).and(finishingCardOk);
}

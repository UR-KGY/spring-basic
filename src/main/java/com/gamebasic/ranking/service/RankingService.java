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

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;


@RequiredArgsConstructor
@Service
public class RankingService {
    private final RankingClient rankingClient;

    public RankingResponse getRankings() {
        RankingSource rankingSource = rankingClient.fetch();

        String name = rankingSource.getMeta().getSeason().getName();

        //우선적으로 순위 대상만을 선별하여 필터링
        List<Record> clearRecords = selectClearRecords(rankingSource.getRecords());
        //정상적인 기록만 남겨두는 필터링 제외된 기록의 수는 excludeCount로 기록
        ExcludeRecordDto dto = excludeWrongRecords(clearRecords);
        int excludeCount = dto.getExcludeCount();
        //필터링된 기록들을 정렬
        List<Record> finalRecords = filterByPlayerId(sortRecord(dto.getRecords()));

        int totalRecords = finalRecords.size();

        //플레이어 순위(인덱스 순서) 를 구하기 위해 IntStream을 사용(for문의 i++ 증감식과 비슷)

        List<Entry> entries = IntStream.range(0, finalRecords.size())
                .mapToObj(i -> {
                    Record r = finalRecords.get(i);
                    return new Entry(i + 1,
                            r.getPlayer().getName(),
                            r.getRun().getDurationSeconds(),
                            r.getRun().getFinalHp(),
                            r.getBossFight().getTotalTurns(),
                            r.getDeck().getSize());  // record 전체가 아니라 필요한 필드만 추출
                })
                .toList();


        return new RankingResponse(
                name,
                totalRecords,
                excludeCount,
                entries
        );

    }

    //run.status 가 CLEARED 이고 run.clearedFloor 가 10인 기록만 반환
    private List<Record> selectClearRecords(List<Record> records) {
        return records.stream()
                .filter(record -> record.getRun().getStatus().equals("CLEARED")
                        && record.getRun().getClearedFloor() == 10)
                .toList();
    }

    private ExcludeRecordDto excludeWrongRecords(List<Record> records) {
        int before = records.size();
        List<Record> result = records.stream()
                .filter(totalPredicate).toList();

        int after = result.size();

        return new ExcludeRecordDto(
                before-after,
                result
        );
    }

    private List<Record> sortRecord(List<Record> records){
        return records.stream().sorted(totalOrder).toList();
    }

    private List<Record> filterByPlayerId(List<Record> records){
        Set<String> set = new HashSet<>();

        return records.stream().
                filter(record ->
                        set.add(record.getPlayer().getId())) //Set 내부에 add 되지 않는다면 false Set은 중복된 값을 저장하지 않으니
                .toList(); //반환되는 리스트에는 가장 앞에 있던 playerId 의 기록들만 남게 된다.

    }



    //todo : 한꺼번에 필터링 후 전 후 비교를 하려다보니 복잡해졌을지도 모른다. 일단 기능 구현에 집중하고 시간 있을 때 리팩토링
    //필터링 타입인 Predicate 를 모아둠
    //run.durationSeconds가 층당 30초 이상, 즉 run.clearedFloor × 30 이상
    Predicate<Record> clearTimeOk = r -> r.getRun().getDurationSeconds() >= r.getRun().getClearedFloor() * 30;
    //run.finalHp가 1 이상 99 이하
    Predicate<Record> hpOk = r -> r.getRun().getFinalHp() >= 1 && r.getRun().getFinalHp() <= 99;
    //deck.cards가 9장 이상 20장 이하이고, deck.size가 deck.cards의 실제 개수와 같음
    Predicate<Record> deckCardsOk = r -> r.getDeck().getCards().size() >= 9 && r.getDeck().getCards().size() <= 20 && r.getDeck().getSize() == r.getDeck().getCards().size();
    //카드가 enum 목록안에 존재하는 카드인지를 확인
    Predicate<Record> cardTypeOk = r -> r.getDeck().getCards().stream().allMatch(card -> Arrays.stream(CardType.values()).anyMatch(cardType -> cardType.name().equals(card.getCardType())));
    //카드를 휙득한 층이 0이상 9이하인지
    Predicate<Record> acquiredFloorOk = r -> r.getDeck().getCards().stream().allMatch(card -> card.getAcquiredFloor() >=0 && card.getAcquiredFloor() <=9);
    //보스 페이즈가 순서대로 THRONE,UNBOUND,ECLIPSE 순서인지 와 각 turns 가 1이상이고  totalTurns 가 세 개의 turns 의 합과 같은지
    Predicate<Record> bossPhaseOk = r-> r.getBossFight().getPhases().stream().map(BossPhase::getPhase).toList().equals(List.of(Phase.THRONE,Phase.UNBOUND,Phase.ECLIPSE))
            && r.getBossFight().getPhases().stream().allMatch(p -> p.getTurns() >=1) && r.getBossFight().getTotalTurns() == r.getBossFight().getPhases().stream().mapToInt(BossPhase::getTurns).sum();
    //bossFight.finishingCard가 그 기록의 deck.cards에 있는 카드 타입
    Predicate<Record> finishingCardOk = r-> r.getDeck().getCards().stream().anyMatch(card -> Objects.equals(card.getCardType(), r.getBossFight().getFinishingCard()));
    //종합적인 제외 조건
    Predicate<Record> totalPredicate = clearTimeOk.and(hpOk).and(deckCardsOk).and(cardTypeOk).and(acquiredFloorOk).and(bossPhaseOk).and(finishingCardOk);

    //정렬 타입인 Comparator를 모아둠
    //우선 run.durationSeconds 으로 오름차순
    Comparator<Record> orderByDurationSecondsAsc= Comparator.comparingInt(record -> record.getRun().getDurationSeconds());
    // run.finalHp 을 기준으로 내림차순
    Comparator<Record> orderByFinalHpDesc = Comparator.comparingInt(record -> record.getRun().getFinalHp());
    // id를 기준으로 오름차순
    Comparator<Record> orderByIdAsc = Comparator.comparingInt(Record::getId);

    //종합적인 정렬 조건 (thenComparing 은 이전 조건에서 동일하여 정렬되지 못한 개체를 정렬)
    Comparator<Record> totalOrder = orderByDurationSecondsAsc.thenComparing(orderByFinalHpDesc).thenComparing(orderByIdAsc);

}

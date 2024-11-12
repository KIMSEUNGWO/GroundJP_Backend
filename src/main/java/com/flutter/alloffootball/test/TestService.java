package com.flutter.alloffootball.test;

import com.flutter.alloffootball.common.domain.field.Address;
import com.flutter.alloffootball.common.domain.field.Field;
import com.flutter.alloffootball.common.domain.field.FieldData;
import com.flutter.alloffootball.common.domain.match.Match;
import com.flutter.alloffootball.common.enums.MatchStatus;
import com.flutter.alloffootball.common.enums.SexType;
import com.flutter.alloffootball.common.enums.field.Parking;
import com.flutter.alloffootball.common.enums.field.Shower;
import com.flutter.alloffootball.common.enums.field.Toilet;
import com.flutter.alloffootball.common.enums.region.Region;
import com.flutter.alloffootball.repository.FieldRepository;
import com.flutter.alloffootball.repository.MatchRepository;
import com.flutter.alloffootball.repository.UserRepository;
import com.flutter.alloffootball.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {

    private final FieldRepository fieldRepository;
    private final MatchRepository matchRepository;

    public void createMockData(Long userId) {
        Field saveField1 = Field.builder()
            .title("테스트 구장 이름")
            .description("테스트 구장 특이사항 내용")
            .address(Address.builder()
                .address("인천 남동구 간석로 1")
                .region(Region.AOMORI)
                .link("https://maps.app.goo.gl/YmMQvP82ZRiKgxcF7")
                .build())
            .fieldData(FieldData.builder()
                .size(123)
                .parking(Parking.FREE)
                .shower(Shower.N)
                .toilet(Toilet.Y)
                .build())
            .build();

        Field saveField2 = Field.builder()
            .title("테스트 구장 이름123123")
            .description("테스트 구장 특이사항 내용123123")
            .address(Address.builder()
                .address("인천 남동구 간석로 1123123")
                .region(Region.GIFU)
                .link("https://maps.app.goo.gl/YmMQvP82ZRiKgxcF7")
                .build())
            .fieldData(FieldData.builder()
                .size(20)
                .parking(Parking.PAID)
                .shower(Shower.Y)
                .toilet(Toilet.N)
                .build())
            .build();

        fieldRepository.save(saveField1);
        fieldRepository.save(saveField2);


        Match saveMatch1 = Match.builder()
            .field(saveField1)
            .matchDate(LocalDateTime.now().plusDays(5))
            .matchTime(2)
            .matchSex(null)
            .matchCount(3)
            .personCount(6)
            .matchStatus(MatchStatus.OPEN)
            .build();

        Match saveMatch2 = Match.builder()
            .field(saveField1)
            .matchDate(LocalDateTime.now().plusDays(1))
            .matchTime(2)
            .matchSex(SexType.MALE)
            .matchCount(3)
            .personCount(6)
            .matchStatus(MatchStatus.OPEN)
            .build();

        Match saveMatch3 = Match.builder()
            .field(saveField2)
            .matchDate(LocalDateTime.now().plusDays(2))
            .matchTime(2)
            .matchSex(SexType.MALE)
            .matchCount(3)
            .personCount(6)
            .matchStatus(MatchStatus.OPEN)
            .build();

        Match saveMatch4 = Match.builder()
            .field(saveField2)
            .matchDate(LocalDateTime.now().plusDays(3))
            .matchTime(2)
            .matchSex(null)
            .matchCount(3)
            .personCount(6)
            .matchStatus(MatchStatus.OPEN)
            .build();

        Match saveMatch5 = Match.builder()
            .field(saveField1)
            .matchDate(LocalDateTime.now().plusDays(7))
            .matchTime(2)
            .matchSex(null)
            .matchCount(3)
            .personCount(6)
            .matchStatus(MatchStatus.OPEN)
            .build();

        matchRepository.save(saveMatch1);
        matchRepository.save(saveMatch2);
        matchRepository.save(saveMatch3);
        matchRepository.save(saveMatch4);
        matchRepository.save(saveMatch5);


    }
}

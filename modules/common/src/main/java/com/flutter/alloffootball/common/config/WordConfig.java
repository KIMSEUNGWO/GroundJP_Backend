package com.flutter.alloffootball.common.config;

import ban.inspector.config.InspectConfig;
import ban.inspector.factory.WordFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WordConfig implements InspectConfig {

    @Override
    public void addBanWords(WordFactory factory) {
        factory.add(List.of(
            "졸라", "시바", "시발",
            "감자", "고구마", "바나나"
        ));
    }

    @Override
    public void addExceptWords(WordFactory factory) {
        factory.add(List.of(
            "고르곤졸라", "시바견", "시발점"
        ));
    }
}

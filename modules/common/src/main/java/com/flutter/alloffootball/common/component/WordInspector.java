package com.flutter.alloffootball.common.component;

import ban.inspector.domain.Word;
import ban.inspector.inspector.Inspector;
import com.flutter.alloffootball.common.exception.BanWordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WordInspector {

    private final Inspector inspector;

    public void inspect(final String... word) {
        List<Word> banWordList = new ArrayList<>();
        for (String s : word) {
            banWordList.addAll(inspector.inspect(s));
        }
        if (!banWordList.isEmpty()) {
            throw new BanWordException(banWordList.stream().map(Word::word).toList());
        }
    }
}

package com.flutter.alloffootball.test;

import ban.inspector.domain.Word;
import ban.inspector.inspector.Inspector;
import com.flutter.alloffootball.common.config.security.CustomUserDetails;
import com.flutter.alloffootball.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final Inspector inspector;

    @GetMapping("/test")
    public ResponseEntity<Response> test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        testService.createMockData(1L);
        return Response.ok();
    }

    @GetMapping("/test/valid")
    public ResponseEntity<Response> testa(@RequestParam("word") String word) {
        List<Word> inspect = inspector.inspect(word);
        return Response.ok(inspect);
    }
}

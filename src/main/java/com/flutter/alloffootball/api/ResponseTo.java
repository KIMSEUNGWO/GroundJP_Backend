package com.flutter.alloffootball.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResponseTo {

    private final RestTemplate restTemplate;

    public <T> T get(String uri, Class<T> clazz) {
        return restTemplate.getForObject(uri, clazz);
    }

    public <T> T getProfile(String uri, Class<T> clazz, HttpEntity<String> httpEntity) {
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, clazz).getBody();
    }

    public <T> T post(String uri, Map<String, String> body, HttpHeaders headers, Class<T> clazz) {
        System.out.println("body = " + body);
        return restTemplate.postForObject(uri, new HttpEntity<>(body, headers), clazz);
    }
}

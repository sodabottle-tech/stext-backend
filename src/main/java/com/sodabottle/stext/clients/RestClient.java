package com.sodabottle.stext.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
@Service
public class RestClient {

    @Autowired
    private RestTemplate restTemplate;

    public <T> void post(String url, Map<String, String> headers, T body) throws URISyntaxException {
        URI uri = new URI(url);

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.entrySet().forEach(header -> httpHeaders.set(header.getKey(), header.getValue()));

        HttpEntity<T> request = new HttpEntity<>(body, httpHeaders);
        restTemplate.postForEntity(uri, request, String.class);
    }
}

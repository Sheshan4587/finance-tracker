package com.financetracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public GeminiService() {
        this.restTemplate = new RestTemplate();
    }

    public String extractExpenseDetails(String sentence) {

        // 1. Build the prompt
        String prompt = """
                Extract expense details from this sentence and return ONLY a JSON object with these fields:
                - description (string)
                - amount (number)
                - category (string, one of: Food & Dining, Transport, Shopping, Entertainment, Health & Medical, Housing & Rent, Utilities, Education, Travel, Savings, Other)
                - date (string in yyyy-MM-dd format, if not specified use today's date: %s)
                
                Sentence: %s
                
                Return ONLY the JSON object, no explanation, no markdown.
                """.formatted(LocalDate.now(), sentence);

        // 2. Build the request body
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        // 3. Build headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // 4. Build the request entity
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 5. Call Gemini API
        Map response = restTemplate.postForObject(
                apiUrl + "?key=" + apiKey,
                entity,
                Map.class
        );

        // 6. Extract the text from response
        List candidates = (List) response.get("candidates");
        Map candidate = (Map) candidates.get(0);
        Map content = (Map) candidate.get("content");
        List parts = (List) content.get("parts");
        Map part = (Map) parts.get(0);

        return (String) part.get("text");
    }
}
package tbank.project.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tbank.project.dto.request.TranslateRequest;
import tbank.project.dto.request.YandexRequest;
import tbank.project.dto.response.TranslateResponse;
import tbank.project.entity.Translator;
import tbank.project.mapper.TranslateMapper;
import tbank.project.repository.impl.TranslateRepositoryImpl;
import tbank.project.services.TranslateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslateServiceImpl implements TranslateService {
    private final TranslateRepositoryImpl repository;
    private static final String URL = "https://translate.api.cloud.yandex.net/translate/v2/translate";

    @Value("${secret.param2}")
    private String folderId;

    @Value("${secret.param1}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public TranslateResponse translate(TranslateRequest translateRequest, HttpServletRequest httpServletRequest) {
        Translator translator = new Translator();
        translator.setRequest(translateRequest.request());
        translator.setIp(getClientIp(httpServletRequest));

        YandexRequest request = new YandexRequest();
        request.setLanguage1(translateRequest.language1());
        request.setTargetLanguageCode(translateRequest.language2());
        request.setTexts(translateRequest.request());
        request.setFolderId(folderId);

        String ans = translateText(translateRequest.request(), translateRequest);


        translator.setAnswer(ans);

        repository.save(translator);

        return TranslateMapper.mapTranslateResponse(translator);
    }

    public Future<String> translateTextUtil(YandexRequest request) {
        return executorService.submit(() -> {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Api-key " + apiKey);
            headers.set("Content-Type", "application/json");

            HttpEntity<YandexRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);

            return Objects.requireNonNull(response.getBody()).substring(response.getBody().indexOf('"', 36) + 1, response.getBody().indexOf('"', 37));
        });
    }

    public String translateText(String text, TranslateRequest translateRequest){

        String lang1 = translateRequest.language1();
        String lang2 = translateRequest.language2();

        String[] words = text.split(" ");

        List<Future<String>> futures = new ArrayList<>();
        for (String word : words) {
            YandexRequest request = new YandexRequest(lang1, lang2, word, folderId);
            futures.add(translateTextUtil(request));
        }

        List<String> translatedWords = new ArrayList<>();
        for (Future<String> future : futures) {
            try {
                translatedWords.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        String translatedText = String.join(" ", translatedWords);
        System.out.println("Translated Text: " + translatedText);

        return translatedText;
    }

    public void shutdown() {
        executorService.shutdown();
    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || remoteAddr.isEmpty()) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }
}

package uaic.info.csft.translationservice.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import uaic.info.csft.translationservice.dto.TranslateDTO;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TranslationService {

    @Value("${deepL.apiKey:6d831dca-c882-4f8a-d68e-77e51dc8751e:fx}")
    private String translateKey;

    @Value("${deepL.endpoint:https://api-free.deepl.com/v2/translate}")
    private String translateEndpoint;

    private final Gson gson = new GsonBuilder().create();

    public String translate(TranslateDTO translateDTO) {
        String response =  WebClient.create(translateEndpoint)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("auth_key", translateKey)
                        .queryParam("source_lang", translateDTO.getSourceLanguage())
                        .queryParam("target_lang", translateDTO.getTargetLanguage())
                        .queryParam("text", translateDTO.getText())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(30));

        JsonObject root = gson.fromJson(response, JsonObject.class);
        JsonArray translations = root.getAsJsonArray("translations");
        JsonObject translationsFirst = translations.get(0).getAsJsonObject();

        return translationsFirst.get("text").getAsString();
    }
}

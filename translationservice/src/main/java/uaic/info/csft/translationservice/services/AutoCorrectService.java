package uaic.info.csft.translationservice.services;

import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uaic.info.csft.translationservice.dto.AutoCorrectDTO;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AutoCorrectService {

    @Value("${languagetool.endpoint:https://api.languagetool.org/v2/check}")
    private String autocorrectEndpoint;

    private final Gson gson = new GsonBuilder().create();

    public Map<String, List<String>> autoCorrect(AutoCorrectDTO autoCorrectDTO)
    {
        String response =  WebClient.create(autocorrectEndpoint)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("language", autoCorrectDTO.getLanguage())
                        .queryParam("text", autoCorrectDTO.getText())
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(30));

        JsonObject root = gson.fromJson(response, JsonObject.class);
        JsonArray matches = root.getAsJsonArray("matches");

        Map<String, List<String>> corrections = new HashMap<>();

        for(JsonElement m : matches)
        {
            JsonObject match = m.getAsJsonObject();

            int offset = match.get("offset").getAsInt();
            int length = match.get("length").getAsInt();
            String correctedWord = autoCorrectDTO.getText().substring(offset, offset + length);

            List<String> wordCorrections = new ArrayList<>();
            JsonArray replacements = match.getAsJsonArray("replacements");

            for(JsonElement r : replacements)
            {
                JsonObject replacement = r.getAsJsonObject();

                String correction = replacement.get("value").getAsString();
                wordCorrections.add(correction);
            }

            corrections.put(correctedWord, wordCorrections);
        }

        return corrections;
    }
}

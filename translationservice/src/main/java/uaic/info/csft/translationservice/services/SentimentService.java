package uaic.info.csft.translationservice.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class SentimentService {

    @Value("${text-processing.endpoint:http://text-processing.com/api/sentiment/}")
    private String sentimentEndpoint;

    private final Gson gson = new GsonBuilder().create();

    public String sentiment(String text)
    {
        String response =  WebClient.create(sentimentEndpoint)
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("text", text))
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(30));

        JsonObject root = gson.fromJson(response, JsonObject.class);
        String label = root.get("label").getAsString();

        if(label.equals("pos"))
        {
            return "positive";
        }
        else if(label.equals("neg"))
        {
            return "negative";
        }

        return "neutral";
    }
}

package uaic.info.csft.translationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.translationservice.dto.AutoCorrectDTO;
import uaic.info.csft.translationservice.dto.TranslateDTO;
import uaic.info.csft.translationservice.services.AutoCorrectService;
import uaic.info.csft.translationservice.services.SentimentService;
import uaic.info.csft.translationservice.services.TranslationService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TranslationController {

    private final TranslationService translationService;
    private final AutoCorrectService autoCorrectService;
    private final SentimentService sentimentService;

    @PostMapping("/translate")
    public String translate(@RequestBody TranslateDTO translateDTO)
    {
        return translationService.translate(translateDTO);
    }

    @PostMapping("/autocorrect")
    public Map<String, List<String>> autoCorrect(@RequestBody AutoCorrectDTO autoCorrectDTO)
    {
        return autoCorrectService.autoCorrect(autoCorrectDTO);
    }

    @PostMapping("/sentiment")
    public String sentiment(@RequestBody String text)
    {
        return sentimentService.sentiment(text);
    }
}

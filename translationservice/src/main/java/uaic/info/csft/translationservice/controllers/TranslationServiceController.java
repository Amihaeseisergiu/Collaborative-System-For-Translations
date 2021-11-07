package uaic.info.csft.translationservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.translationservice.services.TranslationService;

@RestController
@RequestMapping("/v1/translate")
@RequiredArgsConstructor
public class TranslationServiceController {

    private final TranslationService translationService;

    @GetMapping("test")
    public String test() {
        return translationService.test();
    }
}

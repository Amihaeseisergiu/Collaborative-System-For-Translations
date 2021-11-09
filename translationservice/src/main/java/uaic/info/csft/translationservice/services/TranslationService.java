package uaic.info.csft.translationservice.services;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import uaic.info.csft.translationservice.utilities.ApiUtilities;
import uaic.info.csft.translationservice.utilities.JsonUtilities;

import java.util.List;

@Service
@AllArgsConstructor
public class TranslationService {


    public String test() {
        return "test";
    }

    public String translate(String sourceLanguage, String targetLanguage, String phrase) {
        String translationJSON = new ApiUtilities().callTranslationApi(sourceLanguage, targetLanguage, phrase);
        String translation = new JsonUtilities().parseTranslationJSON(translationJSON);
        return translation;
    }

    public List<String> autoCorrect(String language, String word) {
        String autoCorrectJSON = new ApiUtilities().callAutoCorrectApi(language, word);
        List<String> replacements = new JsonUtilities().parseAutoCorrectJSON(autoCorrectJSON);
        return replacements;
    }

}

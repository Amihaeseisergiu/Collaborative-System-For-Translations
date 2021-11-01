package uaic.info.csft.translationservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranslationService {


    public String test() {
        return "test";
    }

    public String translate(String sourceLanguage, String targetLanguage, String phrase) {
        if(targetLanguage.equals("ROMANIAN")) {
            return "cuvinte";
        } else if(targetLanguage.equals("FRENCH")) {
            return "mots";
        } else {
            return "Couldn't translate";
        }
    }

    public String autoCorrect(String language, String word) {
        if(language.equals("ROMANIAN")) {
            return "cuvant";
        } else if(language.equals("ENGLISH")) {
            return "word";
        } else {
            return "Couldn't autocorrect";
        }
    }
}

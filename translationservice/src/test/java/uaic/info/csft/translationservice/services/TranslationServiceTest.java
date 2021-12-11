package uaic.info.csft.translationservice.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uaic.info.csft.translationservice.dto.AutoCorrectDTO;
import uaic.info.csft.translationservice.dto.TranslateDTO;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TranslationServiceTest {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private AutoCorrectService autoCorrectService;

    @Test
    public void testTranslation01() {
        TranslateDTO translateDTO = new TranslateDTO();
        translateDTO.setSourceLanguage("EN");
        translateDTO.setTargetLanguage("RO");
        translateDTO.setText("I like apples");
        String translation = translationService.translate(translateDTO);

        assertEquals(translation, "Îmi plac merele");
    }

    @Test
    public void testTranslation02() {
        TranslateDTO translateDTO = new TranslateDTO();
        translateDTO.setSourceLanguage("EN");
        translateDTO.setTargetLanguage("FR");
        translateDTO.setText("My name is Alex");
        String translation = translationService.translate(translateDTO);

        assertEquals(translation, "Mon nom est Alex");
    }

    @Test
    public void testAutoCorrect01() {
        AutoCorrectDTO autoCorrectDTO = new AutoCorrectDTO();
        autoCorrectDTO.setLanguage("EN-US");
        autoCorrectDTO.setText("wrd");
        Map<String, List<String>> replacements = autoCorrectService.autoCorrect(autoCorrectDTO);

        Assertions.assertTrue(replacements.get("wrd").contains("word"));
    }

    @Test
    public void testAutoCorrect02() {
        AutoCorrectDTO autoCorrectDTO = new AutoCorrectDTO();
        autoCorrectDTO.setLanguage("RO");
        autoCorrectDTO.setText("cvant");

        Map<String, List<String>> replacements = autoCorrectService.autoCorrect(autoCorrectDTO);

        Assertions.assertTrue(replacements.get("cvant").contains("cuvânt"));
    }
}

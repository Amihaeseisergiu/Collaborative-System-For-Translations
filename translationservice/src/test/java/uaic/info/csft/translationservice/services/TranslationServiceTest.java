package uaic.info.csft.translationservice.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TranslationServiceTest {

    private TranslationService translationService;

    @BeforeAll
    public void setUp()
    {
        translationService = new TranslationService();
    }

    @Test
    public void testTranslation01() {
        String sourceLanguage = "EN";
        String targetLanguage = "RO";
        String translation = translationService.translate(sourceLanguage, targetLanguage, "I like apples");

        assertEquals(translation, "Îmi plac merele");
    }

    @Test
    public void testTranslation02() {
        String sourceLanguage = "EN";
        String targetLanguage = "FR";
        String translation = translationService.translate(sourceLanguage, targetLanguage, "My name is Alex");

        assertEquals(translation, "Mon nom est Alex");
    }

    @Test
    public void testAutoCorrect01() {
        String language = "EN-US";
        List<String> replacements = translationService.autoCorrect(language, "wrd");

        Assertions.assertTrue(replacements.contains("word"));
    }

    @Test
    public void testAutoCorrect02() {
        String language = "RO";
        List<String> replacements = translationService.autoCorrect(language, "cvant");

        Assertions.assertTrue(replacements.contains("cuvânt"));
    }
}

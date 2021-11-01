package uaic.info.csft.translationservice.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

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
        String sourceLanguage = "ENGLISH";
        String targetLanguage = "ROMANIAN";
        String translation = translationService.translate(sourceLanguage, targetLanguage, "words");

        assertEquals(translation, "cuvinte");
    }

    @Test
    public void testTranslation02() {
        String sourceLanguage = "ENGLISH";
        String targetLanguage = "FRENCH";
        String translation = translationService.translate(sourceLanguage, targetLanguage, "words");

        assertEquals(translation, "mots");
    }

    @Test
    public void testAutoCorrect01() {
        String language = "ENGLISH";
        String correction = translationService.autoCorrect(language, "wrd");

        assertEquals(correction, "word");
    }

    @Test
    public void testAutoCorrect02() {
        String language = "ROMANIAN";
        String correction = translationService.autoCorrect(language, "cvant");

        assertEquals(correction, "cuvant");
    }
}

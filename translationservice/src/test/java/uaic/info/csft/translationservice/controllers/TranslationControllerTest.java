package uaic.info.csft.translationservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uaic.info.csft.translationservice.dto.AutoCorrectDTO;
import uaic.info.csft.translationservice.dto.TranslateDTO;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TranslationControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void test1Translate() throws Exception
    {
        TranslateDTO translateDTO = new TranslateDTO();
        translateDTO.setSourceLanguage("EN");
        translateDTO.setTargetLanguage("RO");
        translateDTO.setText("I like apples");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(translateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is("Îmi plac merele")));
    }

    @Test
    public void test2AutoCorrect() throws Exception
    {
        AutoCorrectDTO autoCorrectDTO = new AutoCorrectDTO();
        autoCorrectDTO.setLanguage("EN-US");
        autoCorrectDTO.setText("wrd");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/autocorrect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(autoCorrectDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.wrd[?(@ =='word')]", Matchers.contains("word")));
    }

    @Test
    public void test3Sentiment() throws Exception
    {
        String text = "Really bad text";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/sentiment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(text)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is("negative")));
    }

}

package uaic.info.csft.userservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uaic.info.csft.userservice.dto.LoginDTO;
import uaic.info.csft.userservice.dto.RegisterDTO;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Proficiencies;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthControllerTest {

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
    public void test1Register() throws Exception {
        List<Language> languages = new ArrayList<>();
        languages.add(new Language("English", Proficiencies.B2));

        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test Username");
        registerDTO.setPassword("Test Password");
        registerDTO.setLanguages(languages);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();

        assertEquals("Registered Successfully", content);
    }

    @Test
    public void test2Login() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("Test Username");
        loginDTO.setPassword("Test Password");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();

        assertNotEquals(content.length(), 0);

        System.out.println(content);
    }
}

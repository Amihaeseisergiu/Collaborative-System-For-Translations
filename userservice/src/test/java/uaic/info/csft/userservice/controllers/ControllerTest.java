package uaic.info.csft.userservice.controllers;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uaic.info.csft.userservice.dto.LoginDTO;
import uaic.info.csft.userservice.dto.RegisterDTO;
import uaic.info.csft.userservice.entities.Comment;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.entities.Proficiencies;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final ThreadLocal<String> token = new ThreadLocal<>();

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
        token.set(content);
    }

    @Test
    public void test3PutUserLanguages() throws Exception
    {
        Language language = new Language("French", Proficiencies.B2);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(language))
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test4GetUserLanguages() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[?(@.name =='French')].name", Matchers.contains("French")));
    }

    @Test
    public void test5PutUserPost() throws Exception
    {
        Post post = new Post.PostBuilder()
                .title("Test Title")
                .message("Test Message")
                .language(new Language("English", Proficiencies.B2))
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post))
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test6GetUserPosts() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/users/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].title", Matchers.is("Test Title")));
    }

    @Test
    public void test7PutPostComments() throws Exception
    {
        Comment comment = new Comment("Test Comment");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment))
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test80GetPost() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.title", Matchers.is("Test Title")));
    }

    @Test
    public void test81GetPostComments() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].message", Matchers.is("Test Comment")));
    }

    @Test
    public void test90GetLanguageUsers() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/languages/English/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].username", Matchers.is("Test Username")));
    }

    @Test
    public void test91GetLanguagePosts() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/languages/English/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token.get()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].title", Matchers.is("Test Title")));
    }
}

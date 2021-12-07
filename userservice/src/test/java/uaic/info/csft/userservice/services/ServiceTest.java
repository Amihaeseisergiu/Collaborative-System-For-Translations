package uaic.info.csft.userservice.services;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uaic.info.csft.userservice.entities.*;
import uaic.info.csft.userservice.repositories.LanguageRepository;
import uaic.info.csft.userservice.repositories.PostRepository;
import uaic.info.csft.userservice.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LanguageRepository languageRepository;

    private final List<Language> languages = new ArrayList<>();

    @BeforeAll
    public void setUp()
    {
        Faker faker = new Faker();

        languages.add(new Language(faker.nation().language(), Proficiencies.NATIVE));
        languages.add(new Language(faker.nation().language(), Proficiencies.NATIVE));
        languages.add(new Language(faker.nation().language(), Proficiencies.B2));
        languageRepository.saveAll(languages);

        List<User> users = new ArrayList<>();

        LongStream.range(1, 1000)
            .mapToObj(id -> {
                User.UserBuilder userBuilder = new User.UserBuilder();
                User user = userBuilder.id(id)
                        .userName(faker.name().firstName())
                        .password(faker.internet().password())
                        .language(languages.get(0)).language(languages.get(1)).build();
                users.add(user);
                return user;
            })
            .forEach(userRepository::save);

        LongStream.range(1, 1000)
                .mapToObj(id -> {
                    Post.PostBuilder postBuilder = new Post.PostBuilder();
                    postBuilder.id(id)
                            .title(faker.book().title())
                            .message(faker.shakespeare().hamletQuote())
                            .comment(new Comment(users.get((int) id - 1), faker.shakespeare().asYouLikeItQuote()))
                            .language(languages.get(0))
                            .user(users.get((int) id - 1));
                    return postBuilder.build();
                })
                .forEach(postRepository::save);
    }

    @Test
    public void testGetPostComments()
    {
        final Long id = 1L;

        Set<Comment> userComments = postService.getComments(id);

        assertFalse(userComments.isEmpty());

        for(Comment comment : userComments)
        {
            assertEquals(comment.getPost().getId(), id);
        }
    }

    @Test
    public void testGetLanguageUsers()
    {
        Set<User> users = languageService.getLanguageUsers(languages.get(0).getName(), Optional.empty());

        assertNotEquals(users.size(), 0);
    }

    @Test
    public void testGetLanguagePosts()
    {
        Set<Post> posts = languageService.getLanguagePosts(languages.get(0).getName(), Optional.empty());

        assertNotEquals(posts.size(), 0);
    }
}

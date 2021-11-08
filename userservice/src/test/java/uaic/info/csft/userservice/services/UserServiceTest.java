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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

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

        List<AppUser> users = new ArrayList<>();

        LongStream.range(1, 1000)
            .mapToObj(id -> {
                AppUser.AppUserBuilder appUserBuilder = new AppUser.AppUserBuilder();
                AppUser user = appUserBuilder.id(id)
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
    public void testGetUserLanguages()
    {
        final Long id = 1L;

        Set<Language> userLanguages = userService.getUserLanguages(id);

        assertFalse(userLanguages.isEmpty());

        for(Language language : userLanguages)
        {
            assertTrue(language.getAppUsers().stream().anyMatch(u -> u.getId().equals(id)));
        }
    }

    @Test
    public void testAddLanguageToUser() {
        final Long id = 1L;

        userService.addUserLanguage(id, languages.get(2));
        Set<Language> userLanguages = userService.getUserLanguages(id);

        assertEquals(1, (int) userLanguages.stream().filter(l -> l.getName().equals(languages.get(2).getName())).count());
    }

    @Test
    public void testGetUserPosts()
    {
        final Long id = 1L;

        Set<Post> userPosts = userService.getUserPosts(id);

        assertFalse(userPosts.isEmpty());

        for(Post post : userPosts)
        {
            assertEquals(id, (long) post.getAppUser().getId());

            for(Comment comment : post.getComments())
            {
                assertEquals(comment.getPost().getId(), post.getId());
            }
        }
    }

    @Test
    public void testAddPostToUser() {
        final Long id = 1L;
        AppUser replier = userService.findUser(2L);

        Post post = new Post.PostBuilder()
                .title("Test Title")
                .message("Test Message")
                .comment(new Comment(replier, "Test Comment"))
                .language(languages.get(0))
                .build();

        userService.addUserPost(id, post);

        assertEquals(id, post.getAppUser().getId());
    }

    @Test
    public void testGetPostComments()
    {
        final Long id = 1L;

        List<Comment> userComments = postService.getComments(id);

        assertFalse(userComments.isEmpty());

        for(Comment comment : userComments)
        {
            assertEquals(comment.getPost().getId(), id);
        }
    }

    @Test
    public void testPostComment()
    {
        final Long id = 1L;
        AppUser replier = userService.findUser(2L);

        Comment comment = new Comment(replier, "Test message");
        postService.addComment(id, comment);

        assertEquals(comment.getPost().getId(), id);
    }
}

package uaic.info.csft.userservice.services;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import uaic.info.csft.userservice.entities.AppUser;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Proficiencies;
import uaic.info.csft.userservice.exceptions.UserNotFoundException;
import uaic.info.csft.userservice.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);

        Faker faker = new Faker();

        LongStream.range(1, 1000)
            .mapToObj(id -> {
                AppUser.AppUserBuilder appUserBuilder = new AppUser.AppUserBuilder();
                appUserBuilder.id(id)
                        .userName(faker.name().firstName())
                        .password(faker.internet().password())
                        .languages(Arrays.asList(
                                new Language(faker.nation().language(), Proficiencies.B2),
                                new Language(faker.nation().language(), Proficiencies.NATIVE)
                        ));
                return appUserBuilder.build();
            })
            .forEach(userRepository::save);
    }

    @Test
    public void testUserWithIdDoesntExist() {
        final Long id = -1L;

        assertThrows(UserNotFoundException.class, () -> userService.getUserLanguages(id));
    }

    @Test
    public void testUserWithIdExists() {
        final Long id = 1L;

        assertThrows(UserNotFoundException.class, () -> userService.getUserLanguages(id));
    }

    @Test
    public void testAddLanguageToUser() {
        final Long id = 1L;

        userService.addUserLanguage(id, new Language("Romanian", Proficiencies.NATIVE));
        List<Language> userLanguages = userService.getUserLanguages(id);

        assertTrue(userLanguages.stream().anyMatch(l -> l.getName().equals("Romanian")));
    }
}

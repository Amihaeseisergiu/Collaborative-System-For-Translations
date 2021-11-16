package uaic.info.csft.userservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.userservice.aop.TrackExecutionTime;
import uaic.info.csft.userservice.entities.Proficiencies;
import uaic.info.csft.userservice.entities.User;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.repositories.LanguageRepository;

import java.util.*;

@Service
@AllArgsConstructor
@TrackExecutionTime
public class LanguageService {

    private final LanguageRepository languageRepository;

    public Set<User> getLanguageUsers(String name, Optional<List<Proficiencies>> proficiencies)
    {
        List<Language> languages = getLanguages(name, proficiencies);

        Set<User> users = new HashSet<>();

        for(Language language : languages)
        {
            users.addAll(language.getUsers());
        }

        return users;
    }

    public Set<Post> getLanguagePosts(String name, Optional<List<Proficiencies>> proficiencies)
    {
        List<Language> languages = getLanguages(name, proficiencies);

        Set<Post> posts = new HashSet<>();

        for(Language language : languages)
        {
            posts.addAll(language.getPosts());
        }

        return posts;
    }

    private List<Language> getLanguages(String name, Optional<List<Proficiencies>> proficiencies) {
        List<Language> languages = new ArrayList<>();

        if(proficiencies.isEmpty())
        {
            languages = languageRepository.findByName(name);
        }
        else
        {
            List<Proficiencies> profs = proficiencies.get();
            for(Proficiencies p : profs)
            {
                Optional<Language> foundLanguage = languageRepository.findByNameAndProficiency(name, p);

                if(foundLanguage.isPresent())
                {
                    languages.add(foundLanguage.get());
                }
            }
        }
        return languages;
    }
}

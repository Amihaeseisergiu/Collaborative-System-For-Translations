package uaic.info.csft.userservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.userservice.entities.AppUser;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.exceptions.LanguageNotFoundException;
import uaic.info.csft.userservice.repositories.LanguageRepository;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    public Set<AppUser> getLanguageUsers(Long id)
    {
        Optional<Language> foundLanguage = languageRepository.findById(id);

        if(foundLanguage.isPresent())
        {
            Language language = foundLanguage.get();
            return language.getAppUsers();
        }
        else
        {
            throw new LanguageNotFoundException(id);
        }
    }

    public Set<Post> getLanguagePosts(Long id)
    {
        Optional<Language> foundLanguage = languageRepository.findById(id);

        if(foundLanguage.isPresent())
        {
            Language language = foundLanguage.get();
            return language.getPosts();
        }
        else
        {
            throw new LanguageNotFoundException(id);
        }
    }
}

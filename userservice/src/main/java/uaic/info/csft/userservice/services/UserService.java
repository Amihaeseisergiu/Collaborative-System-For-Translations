package uaic.info.csft.userservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.userservice.aop.TrackExecutionTime;
import uaic.info.csft.userservice.entities.AppUser;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.exceptions.LanguageNotFoundException;
import uaic.info.csft.userservice.exceptions.UserNotFoundException;
import uaic.info.csft.userservice.repositories.UserRepository;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@TrackExecutionTime
public class UserService {

    private final UserRepository userRepository;

    public AppUser findUser(Long id)
    {
        Optional<AppUser> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            return foundUser.get();
        }
        else
        {
            throw new UserNotFoundException(id);
        }
    }

    public Set<Language> getUserLanguages(Long id)
    {
        Optional<AppUser> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            return foundUser.get().getLanguages();
        }
        else
        {
            throw new UserNotFoundException(id);
        }
    }

    public void addUserLanguage(Long id, Language language)
    {
        Optional<AppUser> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            AppUser appUser = foundUser.get();
            Set<Language> userLanguages = appUser.getLanguages();

            if(userLanguages.stream().noneMatch(l -> l.getId().equals(language.getId())))
            {
                appUser.addLanguage(language);
            }

            userRepository.saveAndFlush(appUser);
        }
        else
        {
            throw new UserNotFoundException(id);
        }
    }

    public Set<Post> getUserPosts(Long id)
    {
        Optional<AppUser> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            return foundUser.get().getPosts();
        }
        else
        {
            throw new UserNotFoundException(id);
        }
    }

    public void addUserPost(Long id, Post post)
    {
        Optional<AppUser> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            AppUser appUser = foundUser.get();

            if(appUser.getLanguages().stream().noneMatch(l -> l.getId().equals(post.getLanguage().getId())))
            {
                throw new LanguageNotFoundException(post.getLanguage().getId());
            }

            appUser.addPost(post);

            userRepository.saveAndFlush(appUser);
        }
        else
        {
            throw new UserNotFoundException(id);
        }
    }
}

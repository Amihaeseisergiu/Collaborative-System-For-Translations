package uaic.info.csft.userservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.userservice.entities.AppUser;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.exceptions.UserNotFoundException;
import uaic.info.csft.userservice.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public List<Language> getUserLanguages(UUID id)
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

    public void addUserLanguage(UUID id, Language language)
    {
        Optional<AppUser> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            AppUser appUser = foundUser.get();
            appUser.getLanguages().add(language);

            userRepository.saveAndFlush(appUser);
        }
        else
        {
            throw new UserNotFoundException(id);
        }
    }
}

package uaic.info.csft.mainapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.mainapp.clients.TranslationServiceClient;
import uaic.info.csft.mainapp.clients.UserServiceClient;
import uaic.info.csft.mainapp.entities.Language;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ApiGatewayService {

    private final UserServiceClient userServiceClient;
    private final TranslationServiceClient translationServiceClient;

    public String test()
    {
        return translationServiceClient.test();
    }

    public List<Language> getUserLanguages(Long id)
    {
        return userServiceClient.getUserLanguages(id);
    }

    public void addUserLanguage(Long id, Language language)
    {
        userServiceClient.addUserLanguage(id, language);
    }
}

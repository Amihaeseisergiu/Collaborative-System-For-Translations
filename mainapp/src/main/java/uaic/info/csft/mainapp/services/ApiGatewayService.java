package uaic.info.csft.mainapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.mainapp.clients.UserServiceClient;
import uaic.info.csft.mainapp.entities.Language;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ApiGatewayService {

    private final UserServiceClient userServiceClient;

    public List<Language> getUserLanguages(UUID id)
    {
        return userServiceClient.getUserLanguages(id);
    }

    public void addUserLanguage(UUID id, Language language)
    {
        userServiceClient.addUserLanguage(id, language);
    }
}

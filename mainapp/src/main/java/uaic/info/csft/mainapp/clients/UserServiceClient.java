package uaic.info.csft.mainapp.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.mainapp.entities.Language;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@FeignClient("user-service")
public interface UserServiceClient {

    @GetMapping("api/v1/users/{id}/languages")
    List<Language> getUserLanguages(@PathVariable @Valid UUID id);

    @GetMapping("api/v1/users/test")
    String test();

    @PutMapping("api/v1/users/{id}/languages")
    void addUserLanguage(@PathVariable @Valid UUID id, @RequestBody Language language);
}

package uaic.info.csft.mainapp.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.mainapp.entities.Language;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@FeignClient("user-service")
public interface UserServiceClient {

    @GetMapping("api/v1/user/{id}/languages")
    List<Language> getUserLanguages(@PathVariable @Valid @Min(0) UUID id);

    @PutMapping("api/v1/user/{id}/languages")
    void addUserLanguage(@PathVariable @Valid @Min(0) UUID id, @RequestBody Language language);
}

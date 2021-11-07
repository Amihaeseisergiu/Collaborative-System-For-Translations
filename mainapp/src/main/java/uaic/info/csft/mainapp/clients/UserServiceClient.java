package uaic.info.csft.mainapp.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.mainapp.entities.Language;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@FeignClient("user-service")
public interface UserServiceClient {

    @GetMapping("api/v1/users/{id}/languages")
    List<Language> getUserLanguages(@PathVariable @Valid @Min(0) Long id);

    @GetMapping("api/v1/users/test")
    String test();

    @PutMapping("api/v1/users/{id}/languages")
    void addUserLanguage(@PathVariable @Valid @Min(0) Long id, @RequestBody Language language);
}

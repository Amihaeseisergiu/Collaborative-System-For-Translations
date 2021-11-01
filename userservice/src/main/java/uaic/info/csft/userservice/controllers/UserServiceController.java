package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserServiceController {

    private final UserService userService;

    @GetMapping("/{id}/languages")
    List<Language> getUserLanguages(@PathVariable @Valid @Min(0) Long id)
    {
        return userService.getUserLanguages(id);
    }

    @GetMapping("/test")
    String test()
    {
        return "Test";
    }

    @PutMapping("/{id}/languages")
    void addUserLanguage(@PathVariable @Valid @Min(0) Long id, @RequestBody Language language)
    {
        userService.addUserLanguage(id, language);
    }
}

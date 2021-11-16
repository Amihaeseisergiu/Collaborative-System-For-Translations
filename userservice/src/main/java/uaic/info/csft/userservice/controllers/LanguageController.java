package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.userservice.entities.Proficiencies;
import uaic.info.csft.userservice.entities.User;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.services.LanguageService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/{name}/users")
    Set<User> getLanguageUsers(@PathVariable @Valid String name, @RequestParam Optional<List<Proficiencies>> proficiencies)
    {
        return languageService.getLanguageUsers(name, proficiencies);
    }

    @GetMapping("/{name}/posts")
    Set<Post> getLanguagePosts(@PathVariable @Valid String name, @RequestParam Optional<List<Proficiencies>> proficiencies)
    {
        return languageService.getLanguagePosts(name, proficiencies);
    }
}

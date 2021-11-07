package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uaic.info.csft.userservice.entities.AppUser;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.services.LanguageService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequestMapping("/v1/languages")
@RequiredArgsConstructor
public class LanguageServiceController {

    private final LanguageService languageService;

    @GetMapping("/{id}/users")
    Set<AppUser> getLanguageUsers(@PathVariable @Valid @Min(0) Long id)
    {
        return languageService.getLanguageUsers(id);
    }

    @GetMapping("/{id}/posts")
    Set<Post> getLanguagePosts(@PathVariable @Valid @Min(0) Long id)
    {
        return languageService.getLanguagePosts(id);
    }
}

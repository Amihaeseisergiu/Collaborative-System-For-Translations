package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.services.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/languages")
    Set<Language> getUserLanguages()
    {
        return userService.getUserLanguages();
    }

    @PostMapping("/languages")
    void addUserLanguage(@RequestBody Language language)
    {
        userService.addUserLanguage(language);
    }

    @GetMapping("/posts")
    Set<Post> getUserPosts()
    {
        return userService.getUserPosts();
    }

    @PostMapping("/posts")
    void addUserPost(@RequestBody Post post)
    {
        userService.addUserPost(post);
    }
}

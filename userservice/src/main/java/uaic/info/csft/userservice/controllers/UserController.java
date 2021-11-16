package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.userservice.aop.UserSecured;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.services.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    String test()
    {
        return "Test";
    }

    @UserSecured
    @GetMapping("/{id}/languages")
    Set<Language> getUserLanguages(@PathVariable @Valid @Min(0) Long id)
    {
        return userService.getUserLanguages(id);
    }

    @UserSecured
    @PutMapping("/{id}/languages")
    void addUserLanguage(@PathVariable @Valid @Min(0) Long id, @RequestBody Language language)
    {
        userService.addUserLanguage(id, language);
    }

    @UserSecured
    @GetMapping("/{id}/posts")
    Set<Post> getUserPosts(@PathVariable @Valid @Min(0) Long id)
    {
        return userService.getUserPosts(id);
    }

    @UserSecured
    @PutMapping("/{id}/posts")
    void addUserPost(@PathVariable @Valid @Min(0) Long id, @RequestBody Post post)
    {
        userService.addUserPost(id, post);
    }
}

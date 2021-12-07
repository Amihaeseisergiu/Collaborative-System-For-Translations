package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.userservice.entities.Comment;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.services.PostService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    Post getPost(@PathVariable @Valid @Min(0) Long id)
    {
        return postService.getPost(id);
    }

    @GetMapping("/{id}/comments")
    Set<Comment> getPostComments(@PathVariable @Valid @Min(0) Long id)
    {
        return postService.getComments(id);
    }

    @PostMapping("/{id}/comments")
    void addPostComment(@PathVariable @Valid @Min(0) Long id, @RequestBody Comment comment)
    {
        postService.addComment(id, comment);
    }
}

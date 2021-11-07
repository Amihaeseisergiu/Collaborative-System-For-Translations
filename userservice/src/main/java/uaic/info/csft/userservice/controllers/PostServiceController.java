package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.userservice.entities.Comment;
import uaic.info.csft.userservice.services.PostService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostServiceController {

    private final PostService postService;

    @GetMapping("/{id}/comments")
    List<Comment> getPostComments(@PathVariable @Valid @Min(0) Long id)
    {
        return postService.getComments(id);
    }

    @PutMapping("/{id}/comments")
    void addPostComment(@PathVariable @Valid @Min(0) Long id, @RequestBody Comment comment)
    {
        postService.addComment(id, comment);
    }
}

package uaic.info.csft.userservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.userservice.aop.TrackExecutionTime;
import uaic.info.csft.userservice.entities.Comment;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.exceptions.EntityNotFoundException;
import uaic.info.csft.userservice.repositories.PostRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@TrackExecutionTime
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Set<Comment> getComments(Long id)
    {
        Optional<Post> foundPost = postRepository.findById(id);

        if(foundPost.isPresent())
        {
            return foundPost.get().getComments();
        }
        else
        {
            throw new EntityNotFoundException(Post.class, id);
        }
    }

    public void addComment(Long id, Comment comment)
    {
        Optional<Post> foundPost = postRepository.findById(id);
        comment.setUser(userService.getUserFromRequest());

        if(foundPost.isPresent())
        {
            Post post = foundPost.get();

            if(comment.getUser().getLanguages().stream().noneMatch(l -> l.getName().equals(post.getLanguage().getName())
                && l.getProficiency().compareTo(post.getLanguage().getProficiency()) >= 0))
            {
                throw new EntityNotFoundException(Language.class, post.getLanguage().toString());
            }

            post.addComment(comment);
            postRepository.save(post);
        }
        else
        {
            throw new EntityNotFoundException(Post.class, id);
        }
    }
}

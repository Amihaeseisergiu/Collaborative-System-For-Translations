package uaic.info.csft.userservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.userservice.aop.TrackExecutionTime;
import uaic.info.csft.userservice.entities.Comment;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.exceptions.LanguageNotFoundException;
import uaic.info.csft.userservice.exceptions.PostNotFoundException;
import uaic.info.csft.userservice.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@TrackExecutionTime
public class PostService {

    private final PostRepository postRepository;

    public List<Comment> getComments(Long id)
    {
        Optional<Post> foundPost = postRepository.findById(id);

        if(foundPost.isPresent())
        {
            return foundPost.get().getComments();
        }
        else
        {
            throw new PostNotFoundException(id);
        }
    }

    public void addComment(Long id, Comment comment)
    {
        Optional<Post> foundPost = postRepository.findById(id);

        if(foundPost.isPresent())
        {
            Post post = foundPost.get();

            if(comment.getAppUser().getLanguages().stream().noneMatch(l -> l.getId().equals(post.getLanguage().getId())))
            {
                throw new LanguageNotFoundException(post.getLanguage().getId());
            }

            post.addComment(comment);
        }
        else
        {
            throw new PostNotFoundException(id);
        }
    }
}

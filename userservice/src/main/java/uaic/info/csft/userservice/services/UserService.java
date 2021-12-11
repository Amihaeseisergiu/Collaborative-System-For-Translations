package uaic.info.csft.userservice.services;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import uaic.info.csft.userservice.aop.TrackExecutionTime;
import uaic.info.csft.userservice.entities.User;
import uaic.info.csft.userservice.entities.Language;
import uaic.info.csft.userservice.entities.Post;
import uaic.info.csft.userservice.exceptions.EntityNotFoundException;
import uaic.info.csft.userservice.repositories.LanguageRepository;
import uaic.info.csft.userservice.repositories.UserRepository;
import uaic.info.csft.userservice.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@TrackExecutionTime
public class UserService {

    @Value("${jwt.header:Authorization}")
    private String jwtHeader;

    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final JwtUtil jwtUtil;

    public User getUserFromRequest()
    {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;

        HttpServletRequest request = servletRequestAttributes.getRequest();

        final String token = request.getHeader(jwtHeader);
        final Claims claims = jwtUtil.getClaimsFromToken(token);

        final Long userId = Long.valueOf((Integer) claims.get("id"));

        Optional<User> foundUser = userRepository.findById(userId);

        if(foundUser.isPresent())
        {
            return foundUser.get();
        }
        else
        {
            throw new EntityNotFoundException(User.class, userId);
        }
    }

    public Set<Language> getUserLanguages()
    {
        User user = this.getUserFromRequest();

        return user.getLanguages();
    }

    public void addUserLanguage(Language language)
    {
        User user = this.getUserFromRequest();

        Set<Language> userLanguages = user.getLanguages();

        boolean languageNotInUserLanguages = userLanguages.stream()
                .noneMatch(l -> l.equals(language));

        if(languageNotInUserLanguages)
        {
            languageRepository.save(language);
            user.addLanguage(language);

            userRepository.saveAndFlush(user);
        }
    }

    public Set<Post> getUserPosts()
    {
        User user = this.getUserFromRequest();

        return user.getPosts();
    }

    public void addUserPost(Post post)
    {
        User user = this.getUserFromRequest();

        boolean postLanguageNotInUserLanguages = user.getLanguages().stream()
                .noneMatch(l -> l.equals(post.getLanguage()));

        if(postLanguageNotInUserLanguages)
        {
            throw new EntityNotFoundException(Language.class, post.getLanguage().toString());
        }

        user.addPost(post);

        userRepository.saveAndFlush(user);
    }
}

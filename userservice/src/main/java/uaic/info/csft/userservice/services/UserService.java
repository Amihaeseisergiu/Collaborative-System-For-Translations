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
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
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

    public User findUserById(Long id)
    {
        Optional<User> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            return foundUser.get();
        }
        else
        {
            throw new EntityNotFoundException(User.class, id);
        }
    }

    public Set<Language> getUserLanguages(Long userId)
    {
        Optional<User> foundUser = userRepository.findById(userId);

        if(foundUser.isPresent())
        {
            return foundUser.get().getLanguages();
        }
        else
        {
            throw new EntityNotFoundException(User.class, userId);
        }
    }

    public void addUserLanguage(Long id, Language language)
    {
        Optional<User> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            User user = foundUser.get();
            Set<Language> userLanguages = user.getLanguages();

            if(userLanguages.stream().noneMatch(l -> l.equals(language)))
            {
                languageRepository.save(language);
                user.addLanguage(language);

                userRepository.saveAndFlush(user);
            }
        }
        else
        {
            throw new EntityNotFoundException(User.class, id);
        }
    }

    public Set<Post> getUserPosts(Long id)
    {
        Optional<User> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            return foundUser.get().getPosts();
        }
        else
        {
            throw new EntityNotFoundException(User.class, id);
        }
    }

    public void addUserPost(Long id, Post post)
    {
        Optional<User> foundUser = userRepository.findById(id);

        if(foundUser.isPresent())
        {
            User user = foundUser.get();

            if(user.getLanguages().stream().noneMatch(l -> l.equals(post.getLanguage())))
            {
                throw new EntityNotFoundException(Language.class, post.getLanguage().toString());
            }

            user.addPost(post);

            userRepository.saveAndFlush(user);
        }
        else
        {
            throw new EntityNotFoundException(User.class, id);
        }
    }
}

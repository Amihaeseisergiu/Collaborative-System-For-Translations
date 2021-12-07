package uaic.info.csft.userservice.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uaic.info.csft.userservice.dto.LoginDTO;
import uaic.info.csft.userservice.dto.RegisterDTO;
import uaic.info.csft.userservice.entities.User;
import uaic.info.csft.userservice.exceptions.InvalidCredentialsException;
import uaic.info.csft.userservice.exceptions.UsernameExistsException;
import uaic.info.csft.userservice.repositories.LanguageRepository;
import uaic.info.csft.userservice.repositories.UserRepository;
import uaic.info.csft.userservice.utils.JwtUtil;
import uaic.info.csft.userservice.utils.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginDTO loginDTO)
    {
        Optional<User> foundUser = userRepository.findByUsername(loginDTO.getUsername());

        if(foundUser.isEmpty())
        {
            throw new InvalidCredentialsException();
        }

        User user = foundUser.get();

        if(!passwordEncoder.doPasswordsMatch(loginDTO.getPassword(), user.getPassword()))
        {
            throw new InvalidCredentialsException();
        }

        Map<String, Object> userInformation = new HashMap<>();
        userInformation.put("id", user.getId());
        userInformation.put("username", user.getUsername());

        return jwtUtil.generateToken(userInformation);
    }

    public void register(RegisterDTO registerDTO)
    {
        if(userRepository.findByUsername(registerDTO.getUsername()).isPresent())
        {
            throw new UsernameExistsException();
        }

        languageRepository.saveAll(registerDTO.getLanguages());

        User user = new User.UserBuilder()
                .userName(registerDTO.getUsername())
                .password(passwordEncoder.encrypt(registerDTO.getPassword()))
                .languages(registerDTO.getLanguages())
                .build();

        userRepository.saveAndFlush(user);
    }

    public User getUser()
    {
        return userService.getUserFromRequest();
    }
}

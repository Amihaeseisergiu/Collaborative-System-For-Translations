package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.userservice.dto.LoginDTO;
import uaic.info.csft.userservice.dto.RegisterDTO;
import uaic.info.csft.userservice.entities.User;
import uaic.info.csft.userservice.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {

        String token = authService.login(loginDTO);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        System.out.println(registerDTO.getUsername());
        authService.register(registerDTO);

        return new ResponseEntity<>("Registered Successfully", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> user()
    {
        User u = authService.getUser();

        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}

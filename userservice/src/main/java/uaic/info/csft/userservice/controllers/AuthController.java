package uaic.info.csft.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uaic.info.csft.userservice.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String userName) {
        Map<String, Object> userInformation = new HashMap<>();
        userInformation.put("userName", userName);

        String token = jwtUtil.generateToken(userInformation);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String userName) {
        System.out.println("To be persisted");

        return new ResponseEntity<>("Registered Successfully", HttpStatus.OK);
    }
}

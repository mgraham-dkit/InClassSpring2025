package web_patterns.inclassspring2025.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import web_patterns.inclassspring2025.services.AuthService;

@Slf4j
@RestController
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }
}

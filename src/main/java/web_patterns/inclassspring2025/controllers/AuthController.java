package web_patterns.inclassspring2025.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import web_patterns.inclassspring2025.services.AuthService;

import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(path="/login", produces = "application/json")
    public String login(@RequestParam String username, @RequestParam String password){
        validateNullEmpty(username);
        validateNullEmpty(password);

        try{
            boolean authenticated = authService.login(username, password);
             if(!authenticated){
                 throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
             }
             return "FAKE_TOKEN_NOT_GOOD_DO_NOT_USE_THIS_ONE";
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }catch(SQLException e){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private static void validateNullEmpty(String text) {
        if(text == null || text.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}

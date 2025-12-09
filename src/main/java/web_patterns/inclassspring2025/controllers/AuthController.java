package web_patterns.inclassspring2025.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web_patterns.inclassspring2025.services.AuthService;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    private ConcurrentHashMap<String, String> tokenMap;
    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;

        this.tokenMap = new ConcurrentHashMap<>();
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
             // Generate token
            String token = UUID.randomUUID().toString();
            // Store token
            tokenMap.put(token, username);
            // return token
            return token;
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

    @GetMapping(path="/logout")
    public void logout(@RequestHeader String header){
        String token = header.replace("Bearer ", "");
        String loggedOutUser = tokenMap.remove(token);
        if(loggedOutUser == null){
            log.info("logout attempt for invalid token {}", token);
        }
    }


}

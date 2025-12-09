package web_patterns.inclassspring2025.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web_patterns.inclassspring2025.entities.User;
import web_patterns.inclassspring2025.persistence.UserDao;

import java.sql.SQLException;

@Slf4j
@Service
public class AuthService {
    private UserDao userDao;

    public AuthService(UserDao userDao){
        this.userDao = userDao;
    }

    public boolean login(String username, String password) throws SQLException{
        validateNullEmpty(username, "username cannot be null or blank");
        validateNullEmpty(password, "password cannot be null or blank");

        boolean valid = validatePasswordRequirements(password);
        if(!valid){
            throw new IllegalArgumentException("Password did not meet requirements");
        }

        try {
            boolean authenticated = userDao.login(username, password);
            if(!authenticated) {
                log.info("Failed attempt at login for {}", username);
            }
            return authenticated;
        }catch(SQLException e){
            log.error("Database error occurred when {} attempted login}", username);
            throw e;
        }
    }

    private static void validateNullEmpty(String text, String message) {
        if(text == null || text.isBlank()){
            throw new IllegalArgumentException(message);
        }
    }

    private static boolean validatePasswordRequirements(String password) {
        // todo: Add validation logic
        return true;
    }
}

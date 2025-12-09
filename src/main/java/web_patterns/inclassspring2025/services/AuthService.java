package web_patterns.inclassspring2025.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web_patterns.inclassspring2025.persistence.UserDao;

@Slf4j
@Service
public class AuthService {
    private UserDao userDao;

    public AuthService(UserDao userDao){
        this.userDao = userDao;
    }


}

package com.application.signatures.service;

import com.application.signatures.entity.User;
import com.application.signatures.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User checkUser(String login, String password){
        User user = userRepo.findUserByLoginAndPassword(login, password);
        return user;
    }
}

package com.application.signatures.service;

import com.application.signatures.entity.User;
import com.application.signatures.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User checkUser(String login, String password){
        return userRepo.findUserByLoginAndPassword(login, password);
    }
}

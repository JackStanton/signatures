package com.application.signatures.repository;

import com.application.signatures.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<User, Long> {

    User findUserByLoginAndPassword(String login, String password);

}

package com.rexit.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rexit.tutorial.model.User;
import com.rexit.tutorial.repository.UserRepository;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String sendMessage() {
        return "this is a testing message";
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void rollbackTesting(User newUser) throws Exception {
        userRepository.save(newUser);
        throw new Exception("testing error");
    }
}

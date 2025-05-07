package com.rexit.tutorial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rexit.tutorial.enums.Error;
import com.rexit.tutorial.exception.BusinessException;
import com.rexit.tutorial.model.User;
import com.rexit.tutorial.repository.UserRepository;

import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.QueryTimeoutException;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void rollbackTesting(User newUser) throws Exception {
        userRepository.save(newUser);
        throw new Exception("testing error");
    }

    @Transactional
    public User getUserByUsernameWithLock(String username) {
        Optional<User> optionalUser;

        try {
            optionalUser = userRepository.findByUsernameWithLock(username);
        } catch (QueryTimeoutException | LockTimeoutException e) {
            throw new BusinessException(Error.USER_LOCK_TIMEOUT);
        } catch (Exception e) {
            throw new BusinessException(Error.USER_FETCH_BY_ID_ERROR);
        }

        if (!optionalUser.isPresent()) {
            throw new BusinessException(Error.USER_ID_NOT_FOUND);
        }

        return optionalUser.get();
    }
}

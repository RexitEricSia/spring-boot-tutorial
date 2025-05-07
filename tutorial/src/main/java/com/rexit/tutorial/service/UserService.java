package com.rexit.tutorial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.rexit.tutorial.dto.UserPatchDTO;
import com.rexit.tutorial.enums.Error;
import com.rexit.tutorial.exception.BusinessException;
import com.rexit.tutorial.model.User;
import com.rexit.tutorial.repository.UserRepository;

import jakarta.persistence.LockTimeoutException;
import jakarta.persistence.QueryTimeoutException;
import jakarta.validation.Valid;

@Service
@Validated
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

    @Transactional
    public User getUserByIDWithLock(Long id) {
        Optional<User> optionalUser;

        try {
            optionalUser = userRepository.findByIDWithLock(id);
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

    @Transactional(rollbackFor = Exception.class)
    public User updateUser(Long id, @Valid UserPatchDTO dto) {

        Optional<User> optionalUser = null;

        try {
            optionalUser = userRepository.findById(id);
        } catch (Exception e) {
            throw new BusinessException(Error.USER_FETCH_BY_ID_ERROR);
        }

        if (!optionalUser.isPresent()) {
            throw new BusinessException(Error.USER_ID_NOT_FOUND);
        }

        User user = optionalUser.get();

        if (dto.getUsername() != null)
            user.setUsername(dto.getUsername());
        if (dto.getPassword() != null)
            user.setPassword(dto.getPassword());
        if (dto.getRole() != null)
            user.setRole(dto.getRole());
        if (dto.getAge() != null)
            user.setAge(dto.getAge());
        if (dto.getEmail() != null)
            user.setEmail(dto.getEmail());
        if (dto.getRefreshToken() != null)
            user.setRefreshToken(dto.getRefreshToken());
        if (dto.getIpAddress() != null)
            user.setIpAddress(dto.getIpAddress());
        if (dto.getUserAgent() != null)
            user.setUserAgent(dto.getUserAgent());

        return userRepository.save(user);
    }
}

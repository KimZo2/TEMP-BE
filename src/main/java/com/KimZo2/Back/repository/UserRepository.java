package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    boolean existByUserId(String userId);

    Optional<User> findByUserId(String userId);
}

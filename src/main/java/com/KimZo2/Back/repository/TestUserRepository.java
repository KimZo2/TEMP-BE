package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TestUserRepository implements UserRepository  {

    private final Map<String, User> store = new HashMap<>();

    @Override
    public void save(User user) {
        store.put(user.getUserId(), user);
    }

    @Override
    public boolean existByUserId(String userId) {
        return store.containsKey(userId);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(store.get(userId));
    }
}
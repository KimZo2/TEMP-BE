package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository  {

    private final Map<Long, User> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        return store.put(user.getId(), user);
    }

    @Override
    public boolean existByUserId(String userId) {
        return store.containsKey(userId);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

    @Override
    public Optional<User> findByProviderAndProviderId(String provider, String providerId) {
        return store.values().stream()
                .filter(user -> provider.equals(user.getProvider()) &&
                        providerId.equals(user.getProviderId()))
                .findFirst();
    }
}
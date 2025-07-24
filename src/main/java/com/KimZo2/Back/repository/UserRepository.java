package com.KimZo2.Back.repository;

import com.KimZo2.Back.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByNickname(String nickname);

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

}

package com.eDiya.journalApp.repository;

import com.eDiya.journalApp.entity.User;
import com.eDiya.journalApp.enums.AuthProviderType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
    void deleteByUserName(String username);
    User findByEmail(String email);
    Optional<User> findByProviderIdAndAuthProviderType(String providerId, AuthProviderType authProviderType);

}

// contorller --> service --> repository


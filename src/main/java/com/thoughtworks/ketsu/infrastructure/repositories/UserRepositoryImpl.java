package com.thoughtworks.ketsu.infrastructure.repositories;

import com.thoughtworks.ketsu.domain.users.User;
import com.thoughtworks.ketsu.domain.users.UserRepository;

import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(Map<String, Object> info) {
        return null;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(new User());
    }
}

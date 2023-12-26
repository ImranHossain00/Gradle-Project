package com.techCottage.repositories;

import com.techCottage.domain.User;

import java.util.Optional;

// To perform basic operations related to the `user` table.
public interface UserRepo {
    void save(User user);

    boolean findByEmail(String email);
}

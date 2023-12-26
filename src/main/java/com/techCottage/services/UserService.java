package com.techCottage.services;

import com.techCottage.domain.User;
import com.techCottage.dto.SignUpDTO;

// To develop methods that are responsible for managing the business logics related to the user.
public interface UserService {
    void saveUser(SignUpDTO dto);

    boolean emailUnique(SignUpDTO dto);
}

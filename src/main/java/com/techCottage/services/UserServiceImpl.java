package com.techCottage.services;

import com.techCottage.domain.User;
import com.techCottage.dto.SignUpDTO;
import com.techCottage.dto.UserDTO;
import com.techCottage.repositories.UserRepo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// It is an implementation of UserService Class.
public class UserServiceImpl implements UserService{
    private UserRepo repository;

    public UserServiceImpl(UserRepo repository) {
        this.repository = repository;
    }

    @Override
    public void saveUser(SignUpDTO dto) {
        var user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(encryptPassword(dto.getPassword()));

        repository.save(user);
    }

    @Override
    public boolean emailUnique(SignUpDTO dto) {
        return repository.findByEmail(dto.getEmail());
    }

    // To encrypt password which is given by the user.
    // This method operates unidirectionally; even if someone knows
    // the password in hexadecimal format, it is impossible to determine
    // the exact password provided by the user.
    private String encryptPassword(String password) {
        try {
            var digest
                    = MessageDigest.getInstance("SHA-256");
            var bytes
                    = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to encrypt password", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();

        for (var b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}

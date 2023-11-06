package eshoppers.service;

import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.exception.UserNotFoundExcetion;
import com.imran.eshoppers.modeldto.LoginDTO;
import com.imran.eshoppers.modeldto.UserDTO;
import com.imran.eshoppers.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        String encrypted = encryptPassword(userDTO.getPassword());
        var user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encrypted);
        user.setUsername(userDTO.getUsername());

        userRepository.save(user);
    }

    @Override
    public boolean isNotUniqueUsername(UserDTO user) {
        return userRepository.findByUsername(user.getUsername()).isPresent();
    }

    @Override
    public boolean isNotUniqueEmail(UserDTO user) {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }

    @Override
    public User verifyUser(LoginDTO loginDTO) throws UserNotFoundExcetion {
        var user
                = userRepository.
                findByUsername(
                        loginDTO.
                                getUsername())
                .orElseThrow(
                        () -> new UserNotFoundExcetion(
                                "User Not Found by " + loginDTO.getUsername()));
        var encrypted = encryptPassword(loginDTO.getPassword());
        if (user.getPassword().equals(encrypted)) {
            return user;
        } else {
            throw new UserNotFoundExcetion("Incorrect username password");
        }
    }

    private String encryptPassword(String password) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            var bytes
                    = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to encrypt password", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString
                = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append("0");
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

package com.imran.shopping.cart.service.user;

import com.imran.shopping.cart.domain.User;
import com.imran.shopping.cart.dto.user.UserDTO;
import com.imran.shopping.cart.repository.user.UserRepository;

public class UserSeviceImpl implements UserService{

    private UserRepository userRepository;

    public UserSeviceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDTO userDto) {
        String encryted = encryptPassword(userDto.getPassword());
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encryted);
        user.setUsername(userDto.getUsername());

        userRepository.save(user);
    }

    private String encryptPassword(String password) {
        return password;
    }
}

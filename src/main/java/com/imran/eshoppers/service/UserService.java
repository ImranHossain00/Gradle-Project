package com.imran.eshoppers.service;

import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.exception.UserNotFoundExcetion;
import com.imran.eshoppers.modeldto.LoginDTO;
import com.imran.eshoppers.modeldto.UserDTO;

public interface UserService {

    void saveUser(UserDTO userDTO);

    boolean isNotUniqueUsername(UserDTO user);

    boolean isNotUniqueEmail(UserDTO user);

    User verifyUser(LoginDTO loginDTO) throws UserNotFoundExcetion;
}

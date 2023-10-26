package com.bazlur.eshoppers.service;

import com.bazlur.eshoppers.domain.User;
import com.bazlur.eshoppers.exception.UserNotFoundExcetion;
import com.bazlur.eshoppers.modeldto.LoginDTO;
import com.bazlur.eshoppers.modeldto.UserDTO;

public interface UserService {

    void saveUser(UserDTO userDTO);

    boolean isNotUniqueUsername(UserDTO user);

    boolean isNotUniqueEmail(UserDTO user);

    User verifyUser(LoginDTO loginDTO) throws UserNotFoundExcetion;
}

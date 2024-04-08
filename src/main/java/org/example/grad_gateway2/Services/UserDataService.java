package org.example.grad_gateway2.Services;

import org.example.grad_gateway2.DTO.UserDataDTO;
import org.example.grad_gateway2.Entity.UserData;

public interface UserDataService {

    void addUserData(UserDataDTO userDataDTO);

    void updateUserData(UserDataDTO userDataDTO, long id);

    UserData getUserDataByUserId(long userId);

    void deleteUserData(long id);
}

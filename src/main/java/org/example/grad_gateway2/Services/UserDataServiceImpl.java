package org.example.grad_gateway2.Services;

import org.example.grad_gateway2.DAO.UserDataRepository;
import org.example.grad_gateway2.DTO.UserDataDTO;
import org.example.grad_gateway2.Entity.User;
import org.example.grad_gateway2.Entity.UserData;
import org.example.grad_gateway2.Util.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserDataServiceImpl implements UserDataService{

    private final UserDataRepository userDataRepository;

    private final AuthenticationDetails authenticationDetails;

    @Autowired
    public UserDataServiceImpl(UserDataRepository userDataRepository, AuthenticationDetails authenticationDetails) {
        this.userDataRepository = userDataRepository;
        this.authenticationDetails = authenticationDetails;
    }


    @Override
    public void addUserData(UserDataDTO userDataDTO) {

        Optional<User> user = authenticationDetails.getUser();
        if(user.isEmpty()){
            throw new IllegalArgumentException("Unknown user");
        }
        UserData userData = UserData.builder()
                .id(0L)
                .mobile(userDataDTO.getMobile())
                .experience(userDataDTO.getExperience())
                .location(userDataDTO.getAddress())
                .user(user.get())
                .build();
        userDataRepository.save(userData);

    }

    @Override
    public void updateUserData(UserDataDTO userDataDTO, long id) {
        Optional<User> user = authenticationDetails.getUser();
        if(user.isPresent() && !user.get().getRole().equals("ADMIN")&& user.get().getId() != id){
            throw new IllegalArgumentException("Unauthorized");
        }

        UserData userData = userDataRepository.findById(id).orElse(null);
        if(userData != null){
            userData.setMobile(userDataDTO.getMobile());
            userData.setExperience(userDataDTO.getExperience());
            userData.setLocation(userDataDTO.getAddress());
            userDataRepository.save(userData);
        }

    }

    @Override
    public UserData getUserDataByUserId(long userId) {
        Optional<User> user = authenticationDetails.getUser();
        if(user.isPresent() && !user.get().getRole().equals("ADMIN") && user.get().getId() != userId){
            throw new IllegalArgumentException("Unauthorized");
        }
        return userDataRepository.findByUserId(userId);
    }

    @Override
    public void deleteUserData(long id) {
        userDataRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User data not found"));
        userDataRepository.deleteById(id);

    }
}

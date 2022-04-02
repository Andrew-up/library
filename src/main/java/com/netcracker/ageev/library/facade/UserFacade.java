package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.UserDTO;
import com.netcracker.ageev.library.model.users.Users;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(Users user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setStatus(user.getStatus().name());
        userDTO.setRole(user.getERole().name());
        userDTO.setPhone(user.getPhone());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setInfo(user.getInfo());
        return userDTO;
    }
    public UserDTO userToUserDTO2(Users user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setIsRequestCreated(user.getBasketUser().getIsRequestCreated());
        return userDTO;
    }
}

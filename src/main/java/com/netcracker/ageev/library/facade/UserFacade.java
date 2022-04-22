package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.UserDTO;
import com.netcracker.ageev.library.model.users.BasketUser;
import com.netcracker.ageev.library.model.users.Users;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserFacade {

    private final BasketFacade basketFacade;

    public UserFacade(BasketFacade basketFacade) {
        this.basketFacade = basketFacade;
    }

    public UserDTO userToUserDTO(Users user) {
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

    public UserDTO userToUserDTO2(Users user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setBasketUser(user.getBasketUser().stream().filter(BasketUser::getIsRequestCreated).map(basketFacade::basketUsersDTO).collect(Collectors.toList()));
        return userDTO;
    }
}

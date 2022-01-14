package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.view.dto.user.UserRequestDto;
import ua.com.alevel.view.dto.user.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void create(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setAge(userRequestDto.getAge());
        userService.create(user);
    }

    @Override
    public void update(UserRequestDto userRequestDto) {
        User user = userService.findById(userRequestDto.getId());
        if(user != null) {
            user.setFirstName(userRequestDto.getFirstName());
            user.setLastName(userRequestDto.getLastName());
            user.setAge(userRequestDto.getAge());
            userService.update(user);
        }
    }

    @Override
    public void delete(Long id) {
        userService.delete(id);
    }

    @Override
    public UserResponseDto findById(Long id) {
        return new UserResponseDto(userService.findById(id));
    }

    @Override
    public List<UserResponseDto> findAll() {
        List<User> users = userService.findAll();
        return users.
                stream().
                map(UserResponseDto::new).
                collect(Collectors.toList());
    }
}

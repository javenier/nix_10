package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.entity.user.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.view.dto.user.UserRequestDto;
import ua.com.alevel.view.dto.user.UserResponseDto;
import ua.com.alevel.view.dto.webrequest.PageData;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void create(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        userService.create(user);
    }

    @Override
    public void update(UserRequestDto userRequestDto) {
        User user = userService.findById(userRequestDto.getId());
        if (user != null) {
            user.setEmail(userRequestDto.getEmail());
            user.setPassword(userRequestDto.getPassword());
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
    public PageData<UserResponseDto> findAll(WebRequest request) {
        return null;
    }
}

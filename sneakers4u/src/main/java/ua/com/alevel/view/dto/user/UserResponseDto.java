package ua.com.alevel.view.dto.user;

import ua.com.alevel.persistence.entity.user.User;
import ua.com.alevel.view.dto.ResponseDto;

public class UserResponseDto extends ResponseDto {

    private String email;

    public UserResponseDto(User user) {
        super.setId(user.getId());
        super.setCreated(user.getCreated());
        super.setUpdated(user.getUpdated());
        this.email = user.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

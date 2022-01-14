package ua.com.alevel.view.dto.user;

import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.view.dto.ResponseDto;

import java.util.Date;

public class UserResponseDto extends ResponseDto {

    private String firstName;
    private String lastName;
    private Integer age;
    private Date created;

    public UserResponseDto(User user) {
        this.age = user.getAge();
        this.created = user.getCreated();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        super.setId(user.getId());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

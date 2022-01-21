package ua.com.alevel.view.dto.user;

import ua.com.alevel.persistence.entity.user.Client;
import ua.com.alevel.view.dto.ResponseDto;

public class ClientResponseDto extends ResponseDto {

    private String email;
    private boolean enabled;
    private Integer age;
    private String firstName;
    private String lastName;
    private String gender;

    public ClientResponseDto(Client client) {
        super.setId(client.getId());
        super.setCreated(client.getCreated());
        super.setUpdated(client.getUpdated());
        this.email = client.getEmail();
        this.enabled = client.getEnabled();
        this.age = client.getAge();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        if(client.getGender() != null)
            this.gender = client.getGender().name().equals("MALE") ? "Male" : "Female";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

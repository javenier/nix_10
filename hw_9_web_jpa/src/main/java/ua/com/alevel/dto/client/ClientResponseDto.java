package ua.com.alevel.dto.client;

import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.entity.Client;

public class ClientResponseDto extends ResponseDto {

    private String firstName;
    private String lastName;
    private Integer age;
    private Integer bankCount;

    public ClientResponseDto(Client client) {
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.age = client.getAge();
        super.setId(client.getId());
        super.setCreated(client.getCreated());
        super.setUpdated(client.getUpdated());
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

    public Integer getBankCount() {
        return bankCount;
    }

    public void setBankCount(Integer bankCount) {
        this.bankCount = bankCount;
    }
}

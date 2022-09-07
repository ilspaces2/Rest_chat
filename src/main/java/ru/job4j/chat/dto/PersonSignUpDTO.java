package ru.job4j.chat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PersonSignUpDTO {

    @NotBlank(message = "Person name should not be blank")
    private String name;

    @NotBlank(message = "Person password should not be blank")
    @Size(min = 4, max = 16, message = "Person password should be between 4 and 16 characters")
    private String password;

    public PersonSignUpDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

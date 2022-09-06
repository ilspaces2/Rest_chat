package ru.job4j.chat.dto;

public class PersonSignUpDTO {

    private String name;

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

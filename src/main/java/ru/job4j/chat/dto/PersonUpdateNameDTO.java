package ru.job4j.chat.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class PersonUpdateNameDTO {
    @Min(value = 1, message = "Person id should be greater than 0")
    private int id;

    @NotBlank(message = "Person name should not be blank")
    private String name;

    public PersonUpdateNameDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

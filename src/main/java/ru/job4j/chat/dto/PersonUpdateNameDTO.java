package ru.job4j.chat.dto;

public class PersonUpdateNameDTO {
    private int id;
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

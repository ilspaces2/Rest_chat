package ru.job4j.chat.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class RoomUpdateNameDTO {

    @Min(value = 1, message = "Room id should be greater than 0")
    private int id;

    @NotBlank(message = "Room name should not be blank")
    private String roomName;

    public RoomUpdateNameDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}

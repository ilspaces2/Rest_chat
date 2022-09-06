package ru.job4j.chat.dto;

public class RoomUpdateNameDTO {
    private int id;
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

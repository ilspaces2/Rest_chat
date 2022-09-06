package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.dto.RoomUpdateNameDTO;
import ru.job4j.chat.exception.RoomAlreadyExistsException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> findAll() {
        return repository.findAll();
    }

    public Room findById(int id) {
        return findRoom(id);
    }

    public Room save(Room room) {
        if (repository.findRoomByName(room.getName()).isPresent()) {
            throw new RoomAlreadyExistsException("Room already exists");
        }
        return repository.save(room);
    }

    public Room addMessageToRoom(int roomId, Message message) {
        Room room = findRoom(roomId);
        room.addMessage(message);
        return repository.save(room);
    }

    public Room addPersonToRoom(int roomId, Person person) {
        Room room = findRoom(roomId);
        room.addPerson(person);
        return repository.save(room);
    }

    public void updateRoomName(RoomUpdateNameDTO room) {
        Room rzl = findRoom(room.getId());
        rzl.setName(room.getRoomName());
        repository.save(rzl);
    }

    public Room exitPersonFromRoom(int roomId, int personId) {
        Room room = findRoom(roomId);
        if (!room.getPersons().removeIf(person -> person.getId() == personId)) {
            throw new NoSuchElementException("Person is not in the room");
        }
        return repository.save(room);
    }

    public Room deleteMessageFromRoom(int roomId, int msgId, String msgPersonName) {
        Room room = findRoom(roomId);
        if (!room.getMessages().removeIf(msg -> msg.getId() == msgId
                && msg.getPersonName().equals(msgPersonName))) {
            throw new NoSuchElementException("Message is not in the room");
        }
        return repository.save(room);
    }

    public void deleteById(int id) {
        findRoom(id);
        repository.deleteById(id);
    }

    private Room findRoom(int id) {
        var room = repository.findById(id);
        if (room.isEmpty()) {
            throw new NoSuchElementException("Room not found");
        }
        return room.get();
    }
}

package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> findAll() {
        return repository.findAll();
    }

    public Optional<Room> findById(int id) {
        return repository.findById(id);
    }

    public Room save(Room room) {
        return repository.save(room);
    }

    public Room update(Room room) {
        return repository.save(room);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}

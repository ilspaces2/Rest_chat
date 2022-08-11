package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {

    @Query("select r from Room r join fetch r.persons p join fetch p.role where r.id=?1")
    Optional<Room> findById(int id);

    @Query("select r from Room r join fetch r.persons p join fetch p.role")
    List<Room> findAll();
}

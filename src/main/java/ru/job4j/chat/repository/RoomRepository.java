package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Integer> {

    @Query("from Room r left join fetch r.persons p left join fetch p.role where r.id=?1")
    Optional<Room> findById(int id);

    @Query("from Room r left join fetch r.persons p left join fetch p.role")
    List<Room> findAll();

    @Query("select r.name from Room r  where r.name=?1")
    Optional<Room> findRoomByName(String roomName);

}

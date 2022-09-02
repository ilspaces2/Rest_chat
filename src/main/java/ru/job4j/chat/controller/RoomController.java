package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.exception.RoomAlreadyExistsException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("")
    public List<Room> findAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> findRoomById(@PathVariable int roomId) {
        return new ResponseEntity<>(roomService.findById(roomId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }

    @PutMapping("/updRoomName/{roomId}")
    public ResponseEntity<String> updateRoomName(@PathVariable int roomId, @RequestBody String roomName) {
        roomService.updateRoomName(roomId, roomName);
        return new ResponseEntity<>(String.format(" Room name id %s ", roomName), HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int roomId) {
        roomService.deleteById(roomId);
        return new ResponseEntity<>(
                String.format(" RoomId: %d delete", roomId), HttpStatus.OK
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ResponseEntity<String> handleException(RoomAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}

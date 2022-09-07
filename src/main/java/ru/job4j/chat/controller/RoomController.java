package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.dto.RoomUpdateNameDTO;
import ru.job4j.chat.exception.RoomAlreadyExistsException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController implements CheckArguments {

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
        checkArgumentId(roomId, "Room id incorrect");
        return new ResponseEntity<>(roomService.findById(roomId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Room> createRoom(@RequestBody @Valid Room room) {
        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }

    @PatchMapping("/updRoomName")
    public ResponseEntity<String> updateRoomName(@RequestBody @Valid RoomUpdateNameDTO room) {
        roomService.updateRoomName(room);
        return new ResponseEntity<>(String.format(" Room name is %s ", room.getRoomName()), HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int roomId) {
        checkArgumentId(roomId, "Room id incorrect");
        roomService.deleteById(roomId);
        return new ResponseEntity<>(
                String.format(" RoomId: %d delete", roomId), HttpStatus.OK
        );
    }

    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ResponseEntity<String> handleException(RoomAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}

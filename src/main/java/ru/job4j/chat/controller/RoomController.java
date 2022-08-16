package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.chat.exception.RoomAlreadyExistsException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/room")
public class RoomController {

    private static final String API_DEL_MESSAGE = "http://localhost:8080/message/{msgId}";

    private static final String API_ADD_MESSAGE = "http://localhost:8080/message/";

    private final RestTemplate rest;

    private final RoomService roomService;

    public RoomController(RestTemplate rest, RoomService roomService) {
        this.rest = rest;
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

    @PostMapping("/addPerson/{roomId}")
    public ResponseEntity<Void> addPerson(@PathVariable int roomId, @RequestBody Person person) {
        roomService.addPersonToRoom(roomId, person);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addMsg/{roomId}")
    public ResponseEntity<Void> addMessage(@PathVariable int roomId, @RequestBody Message message) {
        roomService.addMessageToRoom(roomId, rest.postForObject(API_ADD_MESSAGE, message, Message.class));
        return ResponseEntity.ok().build();
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

    @DeleteMapping("/delMsg/{roomId}/{msgPersonName}/{msgId}")
    public ResponseEntity<String> deleteMessage(@PathVariable int roomId, @PathVariable String msgPersonName, @PathVariable int msgId) {
        roomService.deleteMessageFromRoom(roomId, msgId, msgPersonName);
        rest.delete(API_DEL_MESSAGE, msgId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/exitRoom/{roomId}/{personId}")
    public ResponseEntity<String> exitRoom(@PathVariable int roomId, @PathVariable int personId) {
        roomService.exitPersonFromRoom(roomId, personId);
        return ResponseEntity.ok().build();
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

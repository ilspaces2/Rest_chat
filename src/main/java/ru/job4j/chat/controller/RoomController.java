package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.util.List;

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

    @GetMapping("/")
    public List<Room> findAllRooms() {
        return roomService.findAll();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> findRoomById(@PathVariable int roomId) {
        var room = roomService.findById(roomId);
        return new ResponseEntity<>(
                room.orElse(null),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }

    @PostMapping("/addPerson/{roomId}")
    public ResponseEntity<String> addPerson(@PathVariable int roomId, @RequestBody Person person) {
        var room = roomService.findById(roomId);
        if (room.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" RoomId: %d not found ", roomId), HttpStatus.NOT_FOUND
            );
        }
        Room rzl = room.get();
        rzl.addPerson(person);
        roomService.update(rzl);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addMsg/{roomId}")
    public ResponseEntity<String> addMessage(@PathVariable int roomId, @RequestBody Message message) {
        var room = roomService.findById(roomId);
        if (room.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" RoomId: %d not found ", roomId), HttpStatus.NOT_FOUND
            );
        }
        Room rzl = room.get();
        rzl.addMessage(rest.postForObject(API_ADD_MESSAGE, message, Message.class));
        roomService.update(rzl);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updRoomName/{roomId}")
    public ResponseEntity<String> updateRoomName(@PathVariable int roomId, @RequestBody String name) {
        var room = roomService.findById(roomId);
        if (room.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" RoomId: %d not found ", roomId), HttpStatus.NOT_FOUND
            );
        }
        Room rzl = room.get();
        rzl.setName(name);
        roomService.update(rzl);
        return new ResponseEntity<>(String.format(" Room name id %s ", name), HttpStatus.OK);
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
        var room = roomService.findById(roomId);
        if (room.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" RoomId: %d not found ", roomId), HttpStatus.NOT_FOUND
            );
        }
        Room rzl = room.get();
        if (!rzl.getMessages().removeIf(msg -> msg.getId() == msgId
                && msg.getPersonName().equals(msgPersonName))) {
            return new ResponseEntity<>(
                    " Message not found in room ", HttpStatus.NOT_FOUND
            );
        }
        roomService.update(rzl);
        rest.delete(API_DEL_MESSAGE, msgId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/exitRoom/{roomId}/{personId}")
    public ResponseEntity<String> exitRoom(@PathVariable int roomId, @PathVariable int personId) {
        var room = roomService.findById(roomId);
        if (room.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" RoomId: %d not found ", roomId), HttpStatus.NOT_FOUND
            );
        }
        Room rzl = room.get();
        if (!rzl.getPersons().removeIf(person -> person.getId() == personId)) {
            return new ResponseEntity<>(
                    " Person not found in room ", HttpStatus.NOT_FOUND
            );
        }
        roomService.update(rzl);
        return ResponseEntity.ok().build();
    }
}

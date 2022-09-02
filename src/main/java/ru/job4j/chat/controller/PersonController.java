package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.exception.UsernameAlreadyExistsException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.service.RoomService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final RoomService roomService;
    private final MessageService messageService;

    public PersonController(PersonService personService, RoomService roomService, MessageService messageService) {
        this.personService = personService;
        this.roomService = roomService;
        this.messageService = messageService;
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> findPersonById(@PathVariable int personId) {
        return new ResponseEntity<>(personService.findById(personId), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/enterRoom/{roomId}/{personId}")
    public ResponseEntity<String> enterRoom(@PathVariable int roomId, @PathVariable int personId) {
        roomService.addPersonToRoom(roomId, personService.findById(personId));
        return new ResponseEntity<>(
                String.format(" PersonId: %d enter the roomId: %d ", personId, roomId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/exitRoom/{roomId}/{personId}")
    public ResponseEntity<String> exitRoom(@PathVariable int roomId, @PathVariable int personId) {
        roomService.exitPersonFromRoom(roomId, personService.findById(personId).getId());
        return new ResponseEntity<>(
                String.format(" PersonId: %d leaving the roomId: %d ", personId, roomId),
                HttpStatus.OK
        );
    }

    @PostMapping("/sendMsg/{roomId}/{personId}")
    public ResponseEntity<String> sendMsg(@RequestBody Message message, @PathVariable int roomId, @PathVariable int personId) {
        message.setPersonName(personService.findById(personId).getName());
        roomService.addMessageToRoom(roomId, messageService.save(message));
        return new ResponseEntity<>(
                " Message send ", HttpStatus.OK
        );
    }

    @DeleteMapping("/delMsg/{roomId}/{personId}/{msgId}")
    public ResponseEntity<String> deleteMsg(@PathVariable int roomId, @PathVariable int personId, @PathVariable int msgId) {
        roomService.deleteMessageFromRoom(roomId, msgId, personService.findById(personId).getName());
        messageService.deleteById(msgId);
        return new ResponseEntity<>(
                " Message delete ", HttpStatus.OK
        );
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleException(UsernameAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleException(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}

package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.chat.exception.UsernameAlreadyExistsException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final String API_ROOM_ADD_PERSON = "http://localhost:8080/room/addPerson/{roomId}";

    private static final String API_ROOM_DEL_PERSON = "http://localhost:8080/room/exitRoom/{roomId}/{personId}";

    private static final String API_ROOM_ADD_MESSAGE = "http://localhost:8080/room/addMsg/{roomId}";

    private static final String API_ROOM_DEL_MESSAGE = "http://localhost:8080/room/delMsg/{roomId}/{msgPersonName}/{msgId}";

    private final PersonService personService;

    private final RestTemplate rest;

    public PersonController(PersonService personService, RestTemplate rest) {
        this.personService = personService;
        this.rest = rest;
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> findPersonById(@PathVariable int personId) {
        var optionalPerson = personService.findById(personId);
        return new ResponseEntity<>(
                optionalPerson.orElse(null),
                optionalPerson.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/enterRoom/{roomId}/{personId}")
    public ResponseEntity<String> enterRoom(@PathVariable int roomId, @PathVariable int personId) {
        var optionalPerson = personService.findById(personId);
        if (optionalPerson.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" PersonId: %d not found ", personId), HttpStatus.NOT_FOUND
            );
        }
        rest.postForObject(API_ROOM_ADD_PERSON, optionalPerson, Person.class, roomId);
        return new ResponseEntity<>(
                String.format(" PersonId: %d enter the roomId: %d ", personId, roomId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/exitRoom/{roomId}/{personId}")
    public ResponseEntity<String> exitRoom(@PathVariable int roomId, @PathVariable int personId) {
        var optionalPerson = personService.findById(personId);
        if (optionalPerson.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" PersonId: %d not found ", personId), HttpStatus.NOT_FOUND
            );
        }
        rest.delete(API_ROOM_DEL_PERSON, roomId, personId);
        return new ResponseEntity<>(
                String.format(" PersonId: %d leaving the roomId: %d ", personId, roomId),
                HttpStatus.OK
        );
    }

    @PostMapping("/sendMsg/{roomId}/{personId}")
    public ResponseEntity<String> sendMsg(@RequestBody Message message, @PathVariable int roomId, @PathVariable int personId) {
        var optionalPerson = personService.findById(personId);
        if (optionalPerson.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" PersonId: %d not found ", personId), HttpStatus.NOT_FOUND
            );
        }
        message.setPersonName(optionalPerson.get().getName());
        rest.postForObject(API_ROOM_ADD_MESSAGE, message, Message.class, roomId);
        return new ResponseEntity<>(
                " Message send ", HttpStatus.OK
        );
    }

    @DeleteMapping("/delMsg/{roomId}/{personId}/{msgId}")
    public ResponseEntity<String> deleteMsg(@PathVariable int roomId, @PathVariable int personId, @PathVariable int msgId) {
        var optionalPerson = personService.findById(personId);
        if (optionalPerson.isEmpty()) {
            return new ResponseEntity<>(
                    String.format(" PersonId: %d not found ", personId), HttpStatus.NOT_FOUND
            );
        }
        rest.delete(API_ROOM_DEL_MESSAGE, roomId, optionalPerson.get().getName(), msgId);
        return new ResponseEntity<>(
                " Message delete ", HttpStatus.OK
        );
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleException(UsernameAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}

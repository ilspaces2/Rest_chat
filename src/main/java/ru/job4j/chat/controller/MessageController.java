package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return new ResponseEntity<>(messageService.save(message), HttpStatus.CREATED);
    }

    @DeleteMapping("/{msgId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int msgId) {
        messageService.deleteById(msgId);
        return ResponseEntity.ok().build();
    }
}

package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.repository.MessageRepository;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Message save(Message message) {
        message.setDatePost(new Date());
        return repository.save(message);
    }

    public void deleteById(int id) {
        if (repository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Message not found");
        }
        repository.deleteById(id);
    }
}

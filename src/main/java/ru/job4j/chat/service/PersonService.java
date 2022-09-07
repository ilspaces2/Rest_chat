package ru.job4j.chat.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.chat.dto.PersonUpdateNameDTO;
import ru.job4j.chat.dto.PersonSignUpDTO;
import ru.job4j.chat.exception.UsernameAlreadyExistsException;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoleRepository;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final PasswordEncoder encoder;

    private final Role defaultRole;

    public PersonService(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.encoder = encoder;
        this.defaultRole = roleRepository.findByName("USER").orElse(null);
    }

    public Person findById(int id) {
        var person = personRepository.findById(id);
        if (person.isEmpty()) {
            throw new NoSuchElementException("Person not found");
        }
        return person.get();
    }

    public void save(PersonSignUpDTO person) {
        if (personRepository.findPersonByName(person.getName()).isPresent()) {
            throw new UsernameAlreadyExistsException("Person already exists");
        }
        Person rzl = new Person();
        rzl.setName(person.getName());
        rzl.setRegistrationDate(new Date());
        rzl.setEnabled(true);
        rzl.addRole(defaultRole);
        rzl.setPassword(encoder.encode(person.getPassword()));
        personRepository.save(rzl);
    }

    public void updatePersonName(PersonUpdateNameDTO person) {
        Person rzl = findById(person.getId());
        rzl.setName(person.getName());
        personRepository.save(rzl);
    }
}

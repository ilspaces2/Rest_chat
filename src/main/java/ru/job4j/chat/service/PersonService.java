package ru.job4j.chat.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.chat.exception.UsernameAlreadyExistsException;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;
import org.postgresql.util.PSQLException;
import ru.job4j.chat.repository.RoleRepository;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public PersonService(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public Person findById(int id) {
        var person = personRepository.findById(id);
        if (person.isEmpty()) {
            throw new NoSuchElementException("Person not found");
        }
        return person.get();
    }

    public void save(Person person) {
        try {
            person.setDateReg(new Date());
            person.setEnabled(true);
            person.setRole(roleRepository.findByName("USER"));
            person.setPassword(encoder.encode(person.getPassword()));
            personRepository.save(person);
        } catch (DataAccessException err) {
            if (err.getMostSpecificCause() instanceof PSQLException
                    && "23505".equals(((PSQLException) err.getMostSpecificCause()).getSQLState())) {
                throw new UsernameAlreadyExistsException(" Username already exists ");
            }
        }
    }
}

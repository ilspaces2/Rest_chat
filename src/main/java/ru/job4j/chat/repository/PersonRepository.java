package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Person;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    @Query("select p from Person p join fetch p.role where p.id=?1")
    Optional<Person> findById(int id);

    @Query("select p from Person p join fetch p.role where p.name=?1")
    Optional<Person> findPersonByName(String personName);
}

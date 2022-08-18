package ru.job4j.chat.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "room_messages",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = " message_id"))
    private List<Message> messages;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "room_persons",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> persons;

    public Room() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messageList) {
        this.messages = messageList;
    }

    public boolean addMessage(Message message) {
        return messages.add(message);
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> personSet) {
        this.persons = personSet;
    }

    public boolean addPerson(Person person) {
        return persons.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

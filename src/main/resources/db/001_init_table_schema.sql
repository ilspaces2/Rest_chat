create table role
(
    id   serial primary key,
    name text NOT NULL unique
);

create table person
(
    id       serial primary key,
    name     text NOT NULL unique,
    date_reg timestamp,
    password text,
    enabled  boolean default true,
    role_id  int  not null references role (id)
);

create table room
(
    id   serial primary key,
    name text unique
);

create table room_persons
(
    room_id   int references room (id),
    person_id int references person (id)
);

create table message
(
    id          serial primary key,
    text        text,
    date_post   timestamp,
    person_name text
);

create table room_messages
(
    room_id    int references room (id),
    message_id int references message (id)
);

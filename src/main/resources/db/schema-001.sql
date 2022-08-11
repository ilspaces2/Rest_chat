create table role
(
    id   serial primary key,
    name VARCHAR(50) NOT NULL unique
);

create table person
(
    id       serial primary key,
    name     VARCHAR(50) NOT NULL unique,
    date_reg timestamp,
    password VARCHAR(200),
    enabled  boolean default true,
    role_id  int         not null references role (id)
);

insert into role (name)
values ('USER');
insert into role (name)
values ('MODER');

insert into person (name, date_reg, password, enabled, role_id)
values ('Main moder', CURRENT_TIMESTAMP,
        '$2a$10$AYcDgu2YpyCqHEacDZURpOGE2lzQfldtUkCyNsYtpwUlKEIeR4Qrq', true,
        (select id from role where name = 'MODER'));

create table if not exists room
(
    id   serial primary key,
    name varchar(200) unique
);

create table if not exists room_persons
(
    room_id   int references room (id),
    person_id int references person (id)
);

create table if not exists message
(
    id          serial primary key,
    text        varchar(1000),
    date_post   timestamp,
    person_name varchar(50)
);

create table if not exists room_messages
(
    room_id    int references room (id),
    message_id int references message (id)
);

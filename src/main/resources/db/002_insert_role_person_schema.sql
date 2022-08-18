insert into role (name)
values ('USER');
insert into role (name)
values ('MODER');

insert into person (name, date_reg, password, enabled)
values ('Main moder', CURRENT_TIMESTAMP,
        '$2a$10$AYcDgu2YpyCqHEacDZURpOGE2lzQfldtUkCyNsYtpwUlKEIeR4Qrq', true);

insert into person_roles (person_id, roles_id)
 values (1,(select id from role where name = 'MODER'));

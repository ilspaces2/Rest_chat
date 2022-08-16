insert into role (name)
values ('USER');
insert into role (name)
values ('MODER');

insert into person (name, date_reg, password, enabled, role_id)
values ('Main moder', CURRENT_TIMESTAMP,
        '$2a$10$AYcDgu2YpyCqHEacDZURpOGE2lzQfldtUkCyNsYtpwUlKEIeR4Qrq', true,
        (select id from role where name = 'MODER'));

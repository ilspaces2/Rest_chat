[![Java CI with Maven](https://github.com/ilspaces2/job4j_chat/actions/workflows/maven.yml/badge.svg)](https://github.com/ilspaces2/job4j_chat/actions/workflows/maven.yml)

### Реализация REST Api чата

- В чате есть различные комнаты с тематическими названиями.
  Можно заходить в любую комнату или сразу в несколько и обмениваться сообщениями.
  Если комната не устраивает то можно из нее выйти.

### Запуск приложения

1. Установить на пк : Java, PostgreSQL, Maven.

2. Через терминал создать базу данных в postgresql c названием chat при помощи команд:

- psql -U yourLogin
- enter yourPassword
- create database chat;

3. Через терминал запустить приложение при помощи maven :

- mvn spring-boot:run

4. Также можно запусть при помощи maven + java

- mvn clean install
- cd target
- java -jar chat-0.0.1.jar

#### Команды для пользователя

- GET /room -получить все комнаты
- GET /room/id -найти комнату по id
- GET /person/id -найти пользователя по id
- POST /person/sign-up -создать пользователя (body - {"name":"userName","password":"userPassword"})
- POST /login -войти в систему (body - {"name":"userName","password":"userPassword"})
- POST /person/enterRoom/roomId/personId - войти в комнату по id комнаты и id пользователя
- DELETE /person/exitRoom/roomId/personId - выйти из комнаты по id комнаты и id пользователя
- POST /person/sendMsg/roomId/personId - отправить сообщение в комнату
  по id комнаты и id пользователя (body - {"text":"msgText"})
- DELETE /person/delMsg/roomId/personId/msgId - удалить сообщение из комнаты
  по id комнаты, id пользователя и id сообщения.

#### Используемые технологии

1. Spring Boot
2. Spring Data
3. PostgreSQL
4. Liquibase
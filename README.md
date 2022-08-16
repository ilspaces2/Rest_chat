[![Java CI with Maven](https://github.com/ilspaces2/job4j_chat/actions/workflows/maven.yml/badge.svg)](https://github.com/ilspaces2/job4j_chat/actions/workflows/maven.yml)

### Реализация REST Api чата.

- В чате есть различные комнаты с тематическими названиями.
  Можно заходить в любую комнату или сразу в несколько и обмениваться сообщениями.
  Если комната не устраивает то можно из нее выйти.

#### Команды для пользователя

- GET /room -получить все комнаты
- GET /room/id -найти комнату по id
- GET /person/id -найти пользователя по id
- POST /person -создать пользователя (body - {"name":"userName","password":"userPassword"})
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
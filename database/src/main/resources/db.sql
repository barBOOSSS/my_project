CREATE DATABASE hotel_db;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS passport;
DROP TABLE IF EXISTS room_user;

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    surname    VARCHAR(50) NOT NULL,
    password   VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL UNIQUE,
    role       VARCHAR(10) NOT NULL,
    city       VARCHAR(50) NULL,
    street     VARCHAR(50) NULL,
    building   VARCHAR(10) NULL,
    flat       VARCHAR(10) NULL,
    created_at DATE        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE passport
(
    user_id         BIGINT      NOT NULL REFERENCES users (id),
    number_passport VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE orders
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT      NOT NULL REFERENCES users (id),
    price        INT         NOT NULL,
    status_order VARCHAR(20) NOT NULL,
    solution     VARCHAR(20) NOT NULL,
    created_at   DATE        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rooms
(
    id          BIGSERIAL PRIMARY KEY,
    number      INT         NOT NULL,
    places      INT         NOT NULL,
    price       INT         NOT NULL,
    class_room  VARCHAR(50) NOT NULL,
    status_room VARCHAR(50) NOT NULL
);

CREATE TABLE room_user
(
    room_id BIGINT REFERENCES rooms (id),
    user_id BIGINT REFERENCES users (id)
);
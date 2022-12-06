--liquibase formatted sql

--changeset airport:1

CREATE TABLE airport
(
    airport VARCHAR(150) PRIMARY KEY,
    country VARCHAR(150) NOT NULL,
    city    VARCHAR(150) NOT NULL
);

--changeset flight:1

CREATE TABLE flight
(
    flight_id      VARCHAR(150) PRIMARY KEY,
    from_id   VARCHAR(150) NOT NULL,
    to_id     VARCHAR(150) NOT NULL,
    carrier        VARCHAR(150) NOT NULL,
    departure_time TIMESTAMP    NOT NULL,
    arrival_time   TIMESTAMP    NOT NULL,
    CONSTRAINT flight_from_id_fkey FOREIGN KEY (from_id) REFERENCES airport (airport),
    CONSTRAINT flight_to_id_fkey FOREIGN KEY (to_id) REFERENCES airport (airport)
);
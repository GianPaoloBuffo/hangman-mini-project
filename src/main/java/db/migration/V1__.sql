CREATE TABLE HangmanUser (
    id serial PRIMARY KEY,
    fullname varchar(255),
    password varchar(255),
    username varchar(255) UNIQUE NOT NULL
);
    
CREATE SEQUENCE hibernate_sequence;

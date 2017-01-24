CREATE TABLE HangmanUser (
    id serial PRIMARY KEY,
    fullname varchar(255),
    password varchar(255),
    username varchar(255) UNIQUE NOT NULL
);

CREATE TABLE HangmanGame (
    id serial PRIMARY KEY,
    word_to_guess VARCHAR(255),
    num_tries_left SMALLINT
);
    
CREATE SEQUENCE hibernate_sequence;

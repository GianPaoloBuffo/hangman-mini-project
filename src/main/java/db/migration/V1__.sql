CREATE TABLE HangmanUser (
    id SERIAL PRIMARY KEY,
    fullname VARCHAR(255),
    password VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE HangmanGame (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    word_to_guess VARCHAR(255),
    guess VARCHAR(255),
    num_tries_left SMALLINT,
    victory BOOLEAN,
    defeat BOOLEAN
);
    
CREATE SEQUENCE hibernate_sequence;

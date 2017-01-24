package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HangmanGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String wordToGuess;
    private byte numTriesLeft;

    public Long getId() {
        return id;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public byte getNumTriesLeft() {
        return numTriesLeft;
    }

    public void setNumTriesLeft(byte numTriesLeft) {
        this.numTriesLeft = numTriesLeft;
    }
}

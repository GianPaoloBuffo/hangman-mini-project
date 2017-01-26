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

    private String username;

    private String word_to_guess;
    private String guess;
    private String guesses;
    private byte num_tries_left;
    private boolean victory;
    private boolean defeat;

    public HangmanGame() {
    }

    public HangmanGame(String username, String word, String guess, byte tries) {
        this.username = username;
        this.word_to_guess = word;
        this.guess = guess;
        this.num_tries_left = tries;
        this.victory = false;
        this.defeat = false;
        this.guesses = "";
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getWordToGuess() {
        return word_to_guess;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getGuesses() {
        return guesses;
    }

    public void setGuesses(String guesses) {
        this.guesses = guesses;
    }

    public void setWordToGuess(String wordToGuess) {
        this.word_to_guess = wordToGuess;
    }

    public byte getNumTriesLeft() {
        return num_tries_left;
    }

    public void setNumTriesLeft(byte numTriesLeft) {
        this.num_tries_left = numTriesLeft;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public boolean isDefeat() {
        return defeat;
    }

    public void setDefeat(boolean defeat) {
        this.defeat = defeat;
    }
}

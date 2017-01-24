package services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dao.HangmanGameDao;
import ninja.Results;
import ninja.Result;

import javax.persistence.EntityManager;
import java.util.Random;

public class HangmanService {

    @Inject
    HangmanGameDao gameDao;

    @Inject
    Provider<EntityManager> entityManagerProvider;

    private final byte MAX_TRIES = 6;

    private String word;
    private byte tries = 0;
    private boolean gameRunning = false;
    private boolean victory = false;
    private boolean defeat = false;

    public String guess = "";

    private String words[] = {
            "seed",
            "load",
            "premium",
            "towering",
            "heap",
            "belong",
            "harmony",
            "true",
            "momentous",
            "icy",
            "bell",
            "imperfect"
    };

    public void startGame() {
        if (!gameRunning) {
            word = getRandomWord(words);

            guess = "";
            for (int i = 0; i < word.length(); i++) {
                guess += "-";
            }

            gameRunning = true;
        }

    }

    public Result guess(char letter) {
        letter = Character.toUpperCase(letter);
        word = word.toUpperCase();

        String newGuess = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                newGuess += letter;
            }
            else {
                newGuess += guess.charAt(i);
            }
        }

        if (newGuess.equals(word)) {
            victory = true;
        }
        else {
            tries++;
            if (tries == MAX_TRIES) {
                guess = word;
                defeat = true;
            }
        }

        if (!defeat) {
            guess = newGuess;
        }

        return Results.redirect("/game");
    }

    public static String getRandomWord(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public String getWord() {
        return word;
    }

    public String getGuess() {
        return guess;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public boolean isVictory() {
        return victory;
    }

    public boolean isDefeat() {
        return defeat;
    }
}

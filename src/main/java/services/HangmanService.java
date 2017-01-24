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

    private String word;
    private boolean gameRunning = false;

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

    // TODO the word is randomizing on each submit
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

        String newGuess = "";
        for (int i = 0; i < word.length(); i++) {
            if (Character.toUpperCase(word.charAt(i)) == letter) {
                newGuess += letter;
            }
            else {
                newGuess += guess.charAt(i);
            }
        }

        guess = newGuess;
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
}

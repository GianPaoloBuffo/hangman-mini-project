package services;

import com.google.inject.Inject;
import dao.HangmanGameDao;
import models.HangmanGame;
import ninja.Result;
import ninja.Results;

import java.util.Random;

public class HangmanService {

    @Inject
    private HangmanGameDao gameDao;

    @Inject
    private HangmanGame game;

    private static final byte MAX_TRIES = 6;

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

    public void startGame(String username) {
        if (!gameRunning) {

            game = gameDao.getGame(username);

            if (game == null) {
                createNewGame(username);
            } else {
                loadGame(game);
            }

            gameRunning = true;
        }

    }

    private void loadGame(HangmanGame game) {
        word = game.getWordToGuess();
        guess = game.getGuess();
        tries = game.getNumTriesLeft();
        victory = game.isVictory();
        defeat = game.isDefeat();
    }

    private void createNewGame(String username) {
        resetGuessAndWord();

        tries = 0;
        victory = false;
        defeat = false;

        game = gameDao.createNewGame(username, word, guess);
    }

    private void resetGuessAndWord() {
        word = getRandomWord(words);

        guess = "";
        for (int i = 0; i < word.length(); i++) {
            guess += "-";
        }
    }

    public Result reset() {
        resetGuessAndWord();

        game.setWordToGuess(word);
        game.setGuess(guess);
        tries = 0;
        game.setNumTriesLeft((byte) 0);
        game.setVictory(false);
        game.setDefeat(false);

        victory = false;
        defeat = false;

        gameDao.saveGame(game);

        return Results.redirect("/game");
    }

    public Result guess(char letter) {
        letter = Character.toUpperCase(letter);
        word = word.toUpperCase();

        String newGuess = "";
        boolean correctGuess = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                newGuess += letter;
                correctGuess = true;
            } else {
                newGuess += guess.charAt(i);
            }
        }

        if (newGuess.equals(word)) {
            victory = true;
            game.setVictory(true);
        } else if (!correctGuess) {
            game.setNumTriesLeft(++tries);

            if (tries == MAX_TRIES) {
                guess = word;
                defeat = true;
                game.setDefeat(true);
            }
        }

        if (!defeat) {
            guess = newGuess;
            game.setGuess(guess);
        }

        saveGame(game);

        return Results.redirect("/game");
    }

    private void saveGame(HangmanGame game) {
        gameDao.saveGame(game);
    }

    private static String getRandomWord(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public String getWord() {
        return word;
    }

    public byte getTries() {
        return tries;
    }

    public String getGuess() {
        return guess;
    }

    public void stopGame() {
        gameRunning = false;
    }

    public boolean isVictory() {
        return victory;
    }

    public boolean isDefeat() {
        return defeat;
    }
}

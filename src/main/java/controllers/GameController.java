package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import services.HangmanService;

@Singleton
public class GameController {

    @Inject
    HangmanService hangmanService;

    public Result game() {
        hangmanService.startGame();

        Result result = Results.html();

        result.render("wordLength", hangmanService.getWord().length());
        result.render("wordGuess", hangmanService.getGuess());
        result.render("gameVictory", hangmanService.isVictory());
        result.render("gameDefeat", hangmanService.isDefeat());

        return result;
    }

    public Result gamePost(@Param("guess") char guess) {
        return hangmanService.guess(guess);
    }
}

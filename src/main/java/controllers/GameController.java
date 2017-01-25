package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import filters.SecureFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.Param;
import ninja.session.Session;
import services.HangmanService;

@Singleton
public class GameController {

    @Inject
    private HangmanService hangmanService;

    @FilterWith(SecureFilter.class)
    public Result game(Session session) {

        String username = session.get("username");

        hangmanService.startGame(username);

        Result result = Results.html();

        result.render("wordLength", hangmanService.getWord().length());
        result.render("wordGuess", hangmanService.getGuess());
        result.render("gameTries", hangmanService.getTries());
        result.render("gameVictory", hangmanService.isVictory());
        result.render("gameDefeat", hangmanService.isDefeat());

        return result;
    }

    public Result gamePost(@Param("guess") char guess) {
        return hangmanService.guess(guess);
    }

    public Result reset(Session session) {
        return hangmanService.reset();
    }

    public Result logout(Context context) {
        // remove any user dependent information
        context.getSession().clear();
        context.getFlashScope().success("login.logoutSuccessful");

        hangmanService.stopGame();

        return Results.redirect("/login");
    }
}

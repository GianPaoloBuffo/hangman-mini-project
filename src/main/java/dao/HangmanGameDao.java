package dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import models.HangmanGame;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import java.util.List;

public class HangmanGameDao {

    @Inject
    Provider<EntityManager> entityManagerProvider;

    @UnitOfWork
    public HangmanGame getGame(String username) {
        EntityManager entityManager = entityManagerProvider.get();

        TypedQuery<HangmanGame> q = entityManager.createQuery("SELECT x FROM HangmanGame x WHERE username = :usernameParam", HangmanGame.class);
        HangmanGame game = getSingleResult(q.setParameter("usernameParam", username));

        if (game == null)
            return null;

        return game;
    }

    /**
     * Get single result without throwing NoResultException, you can of course just catch the
     * exception and return null, it's up to you.
     */
    private static <T> T getSingleResult(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    @Transactional
    public HangmanGame createNewGame(String username, String word, String guess) {
        EntityManager entityManager = entityManagerProvider.get();

        HangmanGame newGame = new HangmanGame(username, word, guess, (byte) 0);
        entityManager.persist(newGame);

        entityManager.setFlushMode(FlushModeType.COMMIT);
        entityManager.flush();

        return newGame;
    }


    @Transactional
    public void saveGame(HangmanGame game) {
        EntityManager entityManager = entityManagerProvider.get();
        entityManager.merge(game);
    }
}
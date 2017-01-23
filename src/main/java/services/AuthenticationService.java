package services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import dao.UserDao;
import models.User;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.session.Session;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

public class AuthenticationService {

    @Inject
    UserDao userDao;

    @Inject
    Provider<EntityManager> entityManagerProvider;

    public Result login(String username, String password, Context context, boolean registering) {
        boolean isUserNameAndPasswordValid = true;
        if (!registering) {
            isUserNameAndPasswordValid = userDao.isUserAndPasswordValid(username, password);
        }

        if (isUserNameAndPasswordValid) {
            Session session = context.getSession();
            session.put("username", username);

            context.getFlashScope().success("login.loginSuccessful");

            return Results.redirect("/");
        } else {
            // something is wrong with the input or password not found.
            context.getFlashScope().put("username", username);
            context.getFlashScope().error("login.errorLogin");

            return Results.redirect("/login");
        }

    }

    @Transactional
    public Result register(String fullname, String username, String password, Context context) {
        EntityManager entityManager = entityManagerProvider.get();

        // Salt and hash password using BCrypt
        password = BCrypt.hashpw(password, BCrypt.gensalt());

        User newUser = new User(username, password, fullname);
        entityManager.persist(newUser);

        entityManager.setFlushMode(FlushModeType.COMMIT);
        entityManager.flush();

        return login(username, password, context, true);
    }
}

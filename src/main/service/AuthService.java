package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDatabase;
import model.Authtoken;
import model.User;

import java.util.UUID;

/**
 * Defines what services can be preformed on a session object
 */
public class AuthService {
    public DataAccess db;

    public AuthService() {
        db = new MemoryDatabase();
    }

    /**
     * Logs a user in
     *
     * @param request for the login
     * @return the attempted login response
     */
    public LoginResponse Login(LoginRequest request) throws DataAccessException {
        User user = db.readUser(request.username());
        if (!user.password().equals(request.password()))
            return new LoginResponse("Error: unauthorized");

        String authToken = UUID.randomUUID().toString();

        try {
            db.writeAuth(new Authtoken(authToken, request.username()));
            return new LoginResponse(request.username(), authToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse("Error: description");
        }
    }

    /**
     * Logs a User out of the system
     *
     * @param username of the User to be logged out
     * @return the attempted Logout response
     */
    public LogoutResponse Logout(String username) {
        if (username.isEmpty())
            return new LogoutResponse("Error: unauthorized");

        try {
            db.removeUser(db.readUser(username));
            return new LogoutResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new LogoutResponse("Error: description");
        }
    }
}

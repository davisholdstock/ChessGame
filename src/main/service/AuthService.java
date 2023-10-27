package service;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import server.server;

import java.util.UUID;

/**
 * Defines what services can be preformed on a session object
 */
public class AuthService {

    public AuthService() {
    }

    /**
     * Logs a user in
     *
     * @param request for the login
     * @return the attempted login response
     */
    public LoginResponse Login(LoginRequest request) throws DataAccessException {
        try {
            //if (server.db.readUser(request.getUsername()) == null)
            User user = server.db.readUser(request.getUsername());
            if (!user.password().equals(request.getPassword()))
                return new LoginResponse("Error: unauthorized", 401);
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse("Error: unauthorized", 401);
        }

        String authToken = UUID.randomUUID().toString();

        try {
            server.db.writeAuth(new AuthToken(authToken, request.getUsername()));
            return new LoginResponse(request.getUsername(), authToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse("Error: description", 500);
        }
    }

    /**
     * Logs a user in for testing purposes only
     *
     * @param request for the login
     * @return the attempted login response
     */
    public LoginResponse TestLogin(LoginRequest request, String authToken) throws DataAccessException {
        User user = server.db.readUser(request.getUsername());
        if (!user.password().equals(request.getPassword()))
            return new LoginResponse("Error: unauthorized", 401);

        try {
            server.db.writeAuth(new AuthToken(authToken, request.getUsername()));
            return new LoginResponse(request.getUsername(), authToken);
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse("Error: description", 500);
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
            return new LogoutResponse("Error: unauthorized", 401);

        try {
            server.db.removeUser(server.db.readUser(username));
            return new LogoutResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return new LogoutResponse("Error: description", 500);
        }
    }
}

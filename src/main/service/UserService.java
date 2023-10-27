package service;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import server.server;

import java.util.UUID;

/**
 * Defines services to be preformed on a User
 */
public class UserService {
    //public DataAccess db;

    public UserService() {
        /* db = new MemoryDatabase(); */
    }

    /**
     * Registers a new User object
     *
     * @param request to register a user
     * @return the attempted registration response
     */
    public RegisterResponse registerUser(RegisterRequest request) throws DataAccessException {
        User user = new User(request.getUsername(), request.getPassword(), request.getEmail());
        String authtoken = UUID.randomUUID().toString();
        try {
            server.db.writeUser(user);
            server.db.writeAuth(new AuthToken(authtoken, request.getUsername()));
            return new RegisterResponse(request.getUsername(), authtoken);
        } catch (Exception e) {
            e.printStackTrace();
            String message = "Error: Unable to Register user";
            return new RegisterResponse(message);
        }
    }
}

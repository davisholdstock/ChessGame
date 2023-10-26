package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDatabase;
import model.Authtoken;
import model.User;

import java.util.UUID;

/**
 * Defines services to be preformed on a User
 */
public class UserService {
    public DataAccess db;

    public UserService() {
        db = new MemoryDatabase();
    }

    /**
     * Registers a new User object
     *
     * @param request to register a user
     * @return the attempted registration response
     */
    public RegisterResponse registerUser(RegisterRequest request) throws DataAccessException {
        User user = new User(request.username(), request.password(), request.email());
        String authtoken = UUID.randomUUID().toString();
        try {
            db.writeUser(user);
            db.writeAuth(new Authtoken(authtoken, request.username()));
            return new RegisterResponse(request.username(), authtoken);
        } catch (Exception e) {
            e.printStackTrace();
            String message = "Error: Unable to Register user";
            return new RegisterResponse(message);
        }
    }
}

package service;

import dataAccess.DataAccessException;
import model.AuthToken;
import model.User;
import requests.RegisterRequest;
import response.RegisterResponse;
import server.server;

import java.util.UUID;

/**
 * Defines services to be preformed on a User
 */
public class UserService {

    public UserService() {
    }

    /**
     * Registers a new User object
     *
     * @param request to register a user
     * @return the attempted registration response
     */
    public RegisterResponse registerUser(RegisterRequest request) {
        String authtoken = UUID.randomUUID().toString();
        User user = new User(request.getUsername(), request.getPassword(), request.getEmail());
        try {
            server.db.writeUser(user);
            server.db.writeAuth(new AuthToken(authtoken, request.getUsername()));
            return new RegisterResponse(request.getUsername(), authtoken);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (server.db.readUser(request.getUsername()) != null) {
                    return new RegisterResponse("Error: already taken", 403);
                }
            } catch (DataAccessException ignored) {
            }
            String message = "Error: description";
            return new RegisterResponse(message, 500);
        }
    }
}

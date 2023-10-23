package service;

import model.Authtoken;

/**
 * Returns a response to POST at the /user endpoint
 *
 * @param message   from the server
 * @param username  of the user
 * @param authToken for the user
 */
public record RegisterResponse(String message, String username, Authtoken authToken) {
}

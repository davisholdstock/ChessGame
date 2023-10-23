package service;

import model.Authtoken;

/**
 * Returns a response from POST on the /session endpoint
 *
 * @param message   from the server
 * @param authToken for the user
 * @param username  for the user
 */
public record LoginResponse(String message, Authtoken authToken, String username) {
}

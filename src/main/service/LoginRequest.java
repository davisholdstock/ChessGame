package service;

/**
 * Requests login of a user from the server
 *
 * @param username of the user
 * @param password of the user
 */
public record LoginRequest(String username, String password) {
}

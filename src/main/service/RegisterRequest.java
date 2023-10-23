package service;

/**
 * Requests registration of a user from the server
 *
 * @param username of the user
 * @param password of the user
 * @param email    of the user
 */
public record RegisterRequest(String username, String password, String email) {
}

package service;

/**
 * Returns a response from DELETE at the /session endpoint
 *
 * @param message from the server
 */
public record LogoutResponse(String message) {
}

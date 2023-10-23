package service;

/**
 * Requests a new Game to be created from the server
 *
 * @param gameName of the Game to be created
 */
public record CreateGameRequest(String gameName) {
}

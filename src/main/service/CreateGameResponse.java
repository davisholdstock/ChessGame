package service;

/**
 * Returns a response from POST on the /game endpoint
 *
 * @param message
 * @param gameID
 */
public record CreateGameResponse(String message, int gameID) {
}

package model;

/**
 * Creates an Auth Token for a User to prove that they are authorized to play a game
 *
 * @param username  of the player who is authorized
 * @param authToken attacked to the session for the user
 */
public record AuthToken(String authToken, String username) {
    @Override
    public String toString() {
        return username + ":" + authToken;
    }
}

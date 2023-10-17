package model;

/**
 * Defines what an Authorization token looks like
 */
public class Authtoken {
    String authToken;
    String username;

    /**
     * Creates an Auth Token for a User to prove that they are authorized to play a game
     *
     * @param username of the player who is authorized
     */
    public Authtoken(String username) {
    }
}

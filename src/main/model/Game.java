package model;

/**
 * Creates a new game for two players
 *
 * @param gameName      is the name of the game
 * @param game
 * @param gameID        attached to the game
 * @param whiteUsername
 * @param blackUsername
 */
public record Game(String gameName, main.Game game, String whiteUsername, String blackUsername, int gameID) {
    @Override
    public String toString() {
        return "Game: " + gameName + "(" + gameID + ")"
                + "\nPlayers:"
                + "\nWhite: " + whiteUsername
                + "\nBlack: " + blackUsername;
    }
}

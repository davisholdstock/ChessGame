package model;

import chess.ChessGame;

/**
 * Creates a new game for two players
 *
 * @param gameName is the name of the game
 */
public record Game(String gameName, ChessGame game, String whiteusername, String blackUsername, int gameID) {
    @Override
    public String toString() {
        return "Game: " + gameName + "(" + gameID + ")"
                + "\nPlayers:"
                + "\nWhite: " + whiteusername
                + "\nBlack: " + blackUsername
                + game.toString();
    }
}

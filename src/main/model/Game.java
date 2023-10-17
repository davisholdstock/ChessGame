package model;

import chess.ChessGame;

/**
 * Defines what a Game object looks like
 */
public class Game {
    int gameID;
    String whiteUsername;
    String blackUsername;
    String gameName;
    ChessGame game;

    /**
     * Creates a new game for two players
     *
     * @param gameName is the name of the game
     */
    public Game(String gameName) {
    }
}

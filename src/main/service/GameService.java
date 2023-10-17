package service;

import chess.ChessGame;

import java.util.ArrayList;

/**
 * Defines services that can be preformed on a Game object
 */
public class GameService {

    public GameService() {
    }

    /**
     * Creates a new Game
     *
     * @param gameName of the game being created
     */
    public void newGame(String gameName) {
    }

    /**
     * Joins a User to a Game
     *
     * @param gameID of the game to join
     */
    public void joinGame(int gameID) {
    }

    /**
     * Lists all the saved games
     *
     * @return an ArrayList of all the saved games
     */
    public ArrayList<ChessGame> listGames() {
        return null;
    }
}

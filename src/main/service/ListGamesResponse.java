package service;

import main.Game;

import java.util.ArrayList;

/**
 * Returns a response from GET on the /game endpoint
 */
public class ListGamesResponse {
    String message;
    ArrayList<Game> gameList;

    /**
     * Creates a valid list games response
     *
     * @param gameList of the saved games
     */
    public ListGamesResponse(ArrayList<Game> gameList) {
        this.gameList = gameList;
    }

    /**
     * Creates an error list games response
     *
     * @param message from the server
     */
    public ListGamesResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public void setGameList(ArrayList<Game> gameList) {
        this.gameList = gameList;
    }
}

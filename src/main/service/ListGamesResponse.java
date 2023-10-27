package service;

import java.util.ArrayList;

/**
 * Returns a response from GET on the /game endpoint
 */
public class ListGamesResponse {
    String message;
    ArrayList<model.Game> gameList;

    /**
     * Creates a valid list games response
     *
     * @param gameList of the saved games
     */
    public ListGamesResponse(ArrayList<model.Game> gameList) {
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

    public ArrayList<model.Game> getGameList() {
        return gameList;
    }

    public void setGameList(ArrayList<model.Game> gameList) {
        this.gameList = gameList;
    }
}

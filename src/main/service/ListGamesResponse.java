package service;

import java.util.ArrayList;

/**
 * Returns a response from GET on the /game endpoint
 */
public class ListGamesResponse {
    String message;
    ArrayList<model.Game> gameList;
    int STATUS_CODE;

    /**
     * Creates a valid list games response
     *
     * @param gameList of the saved games
     */
    public ListGamesResponse(ArrayList<model.Game> gameList) {
        this.gameList = gameList;
        this.STATUS_CODE = 200;
    }

    /**
     * Creates an error list games response
     *
     * @param message from the server
     */
    public ListGamesResponse(String message, int STATUS_CODE) {
        this.message = message;
        this.STATUS_CODE = STATUS_CODE;
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

    @Override
    public String toString() {
        return "ListGamesResponse{" +
                "message='" + message + '\'' +
                ", gameList: " + ((gameList == null) ? "[]" : gameList.toString()) +
                '}';
    }

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(int STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }
}

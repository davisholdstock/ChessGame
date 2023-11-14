package response;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Returns a response from GET on the /game endpoint
 */
public class ListGamesResponse {
    String message;
    ArrayList<model.Game> games;
    int STATUS_CODE;

    /**
     * Creates a valid list games response
     *
     * @param gameList of the saved games
     */
    public ListGamesResponse(ArrayList<model.Game> gameList) {
        this.games = gameList;
        this.STATUS_CODE = 200;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListGamesResponse that = (ListGamesResponse) o;
        return STATUS_CODE == that.STATUS_CODE && Objects.equals(message, that.message) && Objects.equals(games, that.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, games, STATUS_CODE);
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

    public ArrayList<model.Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<model.Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "ListGamesResponse{" +
                "message='" + message + '\'' +
                ", gameList: " + ((games == null) ? "[]" : games.toString()) +
                '}';
    }

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(int STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }
}

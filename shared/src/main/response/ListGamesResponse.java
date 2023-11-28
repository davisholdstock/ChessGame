package response;

import model.listAllGameObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Returns a response from GET on the /game endpoint
 */
public class ListGamesResponse {
    String message;
    ArrayList<listAllGameObject> games;
    int STATUS_CODE;

    /**
     * Creates a valid list games response
     *
     * @param gameList of the saved games
     */
    public ListGamesResponse(ArrayList<model.Game> gameList) {
//        this.games = gameList;
        games = new ArrayList<>();
        for (var game : gameList) {
            this.games.add(new listAllGameObject(game.gameName(), game.gameID(), game.whiteUsername(), game.blackUsername()));
        }
        this.STATUS_CODE = 200;
    }

//    public ListGamesResponse(String string) {
//        String[] tmpStr = string.split("//");
//        this.message = tmpStr[0];
//        this.games = new ArrayList<>();
//        for (int i = 1; i < tmpStr.length; ++i) {
//            String[] tmp = tmpStr[i].split(">>");
//            Game tmpGame = new Game(tmp[0], new chess.Game(tmp[4]), tmp[1], tmp[2], Integer.parseInt(tmp[3]));
//            games.add(tmpGame);
//        }
//
//    }

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

    public ArrayList<listAllGameObject> getGames() {
        return games;
    }

    public void setGames(ArrayList<listAllGameObject> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (message != null)
            string.append(message);
        if (games != null && !games.isEmpty()) {
            for (int i = 0; i < games.size(); ++i) {
                string.append("" + (i + 1) + ".\n");
                string.append(">> Name: ");
                string.append(games.get(i).getGameName());
                string.append(">> White: ");
                string.append(games.get(i).getWhiteUsername());
                string.append(">> Black: ");
                string.append(games.get(i).getBlackUsername());
                string.append(">> GameID: ");
                string.append(games.get(i).getGameID());
                string.append("\n");
            }
        }
        return string.toString();
    }

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(int STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }
}

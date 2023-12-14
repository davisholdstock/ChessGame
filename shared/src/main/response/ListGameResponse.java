package response;

import java.util.Objects;

public class ListGameResponse {
    String message;
    String game;
    int STATUS_CODE;

    /**
     * Creates a valid list games response
     *
     * @param game
     */
    public ListGameResponse(model.Game game) {
        this.game = game.game().fenNotation();
        this.STATUS_CODE = 200;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListGamesResponse that = (ListGamesResponse) o;
        return STATUS_CODE == that.STATUS_CODE && Objects.equals(message, that.message) && Objects.equals(game, that.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, game, STATUS_CODE);
    }

    /**
     * Creates an error list games response
     *
     * @param message from the server
     */
    public ListGameResponse(String message, int STATUS_CODE) {
        this.message = message;
        this.STATUS_CODE = STATUS_CODE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGames() {
        return game;
    }

    public void setGames(String game) {
        this.game = game;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (message != null)
            string.append(message);
        else
            string.append(this.game);
        return string.toString();
    }

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(int STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }
}

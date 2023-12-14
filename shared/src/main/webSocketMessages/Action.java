package webSocketMessages;

import com.google.gson.Gson;

public record Action(Type type, String username, chess.ChessGame.TeamColor teamColor) {
    public enum Type {
        JOIN,
        LEAVE,
        RESIGN
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
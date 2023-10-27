package service;

import chess.ChessGame;
import model.AuthToken;

public class JoinGameRequest {
    private ChessGame.TeamColor playerColor;
    private int gameID;
    private AuthToken authToken;

    /**
     * Requests to join a Game from the server
     *
     * @param playerColor color of the user trying to join a Game
     * @param gameID      of the Game trying to be joined
     */
    public JoinGameRequest(ChessGame.TeamColor playerColor, int gameID, AuthToken authToken) {
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.authToken = authToken;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(ChessGame.TeamColor playerColor) {
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "JoinGameRequest{" +
                "playerColor=" + playerColor +
                ", gameID=" + gameID +
                '}';
    }
}

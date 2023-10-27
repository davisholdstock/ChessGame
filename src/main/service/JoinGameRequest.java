package service;

import chess.ChessGame;

public class JoinGameRequest {
    ChessGame.TeamColor playerColor;
    int gameID;

    /**
     * Requests to join a Game from the server
     *
     * @param playerColor color of the user trying to join a Game
     * @param gameID      of the Game trying to be joined
     */
    public JoinGameRequest(ChessGame.TeamColor playerColor, int gameID) {
        this.gameID = gameID;
        this.playerColor = playerColor;
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

    @Override
    public String toString() {
        return "JoinGameRequest{" +
                "playerColor=" + playerColor +
                ", gameID=" + gameID +
                '}';
    }
}

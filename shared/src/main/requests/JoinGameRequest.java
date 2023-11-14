package requests;

import chess.ChessGame;

public class JoinGameRequest {
    private ChessGame.TeamColor playerColor;
    private Integer gameID;

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

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
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

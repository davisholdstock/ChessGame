package service;

/**
 * Returns a response from POST on the /game endpoint
 */
public class CreateGameResponse {
    String message;
    int gameID;

    /**
     * Creates a valid create game response
     *
     * @param gameID of the created game
     */
    public CreateGameResponse(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Creates an error create game response
     *
     * @param message from the server
     */
    public CreateGameResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    @Override
    public String toString() {
        return "CreateGameResponse{" +
                "message='" + message + '\'' +
                ", gameID=" + gameID +
                '}';
    }
}

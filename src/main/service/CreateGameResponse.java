package service;

/**
 * Returns a response from POST on the /game endpoint
 */
public class CreateGameResponse {
    String message;
    int gameID;
    int STATUS_CODE;

    /**
     * Creates a valid create game response
     *
     * @param gameID of the created game
     */
    public CreateGameResponse(int gameID) {
        this.gameID = gameID;
        this.STATUS_CODE = 200;
    }

    /**
     * Creates an error create game response
     *
     * @param message from the server
     */
    public CreateGameResponse(String message, int STATUS_CODE) {
        this.message = message;
        this.STATUS_CODE = STATUS_CODE;
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

    public int getSTATUS_CODE() {
        return STATUS_CODE;
    }

    public void setSTATUS_CODE(int STATUS_CODE) {
        this.STATUS_CODE = STATUS_CODE;
    }
}

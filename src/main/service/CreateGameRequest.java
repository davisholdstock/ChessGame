package service;

public class CreateGameRequest {
    String gameName;

    /**
     * Requests a new Game to be created from the server
     *
     * @param gameName of the Game to be created
     */
    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString() {
        return "CreateGameRequest{" +
                "gameName='" + gameName + '\'' +
                '}';
    }
}

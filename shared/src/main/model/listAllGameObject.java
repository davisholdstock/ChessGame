package model;

import java.util.Objects;

public class listAllGameObject {
    String gameName;
    String fenNotation;
    int gameID;
    String whiteUsername;
    String blackUsername;

    public listAllGameObject(String gameName, int gameID, String fenNotation, String whiteUsername, String blackUsername) {
        this.blackUsername = blackUsername;
        this.gameID = gameID;
        this.gameName = gameName;
        this.whiteUsername = whiteUsername;
        this.fenNotation = fenNotation;
    }

    public String getGameName() {
        return gameName;
    }

    public String getFenNotation() {
        return fenNotation;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setFenNotation(String fenNotation) {
        this.fenNotation = fenNotation;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        listAllGameObject that = (listAllGameObject) o;
        return gameID == that.gameID && Objects.equals(gameName, that.gameName) && Objects.equals(whiteUsername, that.whiteUsername) && Objects.equals(blackUsername, that.blackUsername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, gameID, whiteUsername, blackUsername);
    }

    @Override
    public String toString() {
        return "listAllGameObject{" +
                "gameName='" + gameName + '\'' +
                ", gameID=" + gameID +
                ", whiteUsername='" + whiteUsername + '\'' +
                ", blackUsername='" + blackUsername + '\'' +
                '}';
    }
}

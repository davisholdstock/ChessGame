package webSocketMessages;

import com.google.gson.Gson;

public record Notification(Type type, String message) {
    public enum Type {
        JOINED_GAME,
        MOVED,
        LEFT_GAME,
        RESIGNED_GAME
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
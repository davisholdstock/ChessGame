package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.Action;
import webSocketMessages.Notification;
import webSocketMessages.ResponseException;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        Action action = new Gson().fromJson(message, Action.class);
        switch (action.type()) {
            case JOIN -> join(action.visitorName(), session);
            case LEAVE -> leave(action.visitorName());
        }
    }

    private void join(String username, Session session) throws IOException {
        connections.add(username, session);
        var message = String.format("%s joined the game", username);
        var notification = new Notification(Notification.Type.JOINED_GAME, message);
        connections.broadcast(username, notification);
    }

    private void leave(String username) throws IOException {
        connections.remove(username);
        var message = String.format("%s left the game", username);
        var notification = new Notification(Notification.Type.LEFT_GAME, message);
        connections.broadcast(username, notification);
    }

    public void makeMove(String startPosition, String endPosition) throws ResponseException {
        try {
            var message = String.format("Moved from %s to %s", startPosition, endPosition);
            var notification = new Notification(Notification.Type.MOVED, message);
            connections.broadcast("", notification);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
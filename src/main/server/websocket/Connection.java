package server.websocket;

import chess.ChessGame;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public String username;
    public ChessGame.TeamColor teamColor;
    public Session session;

    public Connection(String username, ChessGame.TeamColor teamColor, Session session) {
        this.username = username;
        this.teamColor = teamColor;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}
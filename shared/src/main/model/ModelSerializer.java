package model;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.StringReader;

public class ModelSerializer {
    public static <T> T deserialize(String json, Class<T> responseClass) {
        return deserialize(new StringReader(json), responseClass);
    }

    public static <T> T deserialize(Reader reader, Class<T> responseClass) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ChessGame.class, new GameAdapter());
            gsonBuilder.registerTypeAdapter(ChessBoard.class, new BoardAdapter());
            gsonBuilder.registerTypeAdapter(ChessPiece.class, new PieceAdapter());

            return gsonBuilder.create().fromJson(reader, responseClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

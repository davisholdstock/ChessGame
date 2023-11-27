package model;

import chess.Board;
import chess.ChessBoard;
import com.google.gson.*;

import java.lang.reflect.Type;

public class BoardAdapter implements JsonDeserializer<ChessBoard> {
    @Override
    public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Gson().fromJson(jsonElement, Board.class);
    }
}

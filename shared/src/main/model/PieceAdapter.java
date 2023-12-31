package model;

import chess.ChessPiece;
import chess.Piece;
import com.google.gson.*;

import java.lang.reflect.Type;

public class PieceAdapter implements JsonDeserializer<ChessPiece> {
    @Override
    public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Gson().fromJson(jsonElement, Piece.class);
    }
}

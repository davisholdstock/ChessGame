import chess.*;

import java.util.Collection;

public class PieceImpl implements ChessPiece {
    public ChessGame.TeamColor color;
    public PieceType type;

    public PieceImpl(ChessGame.TeamColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }
    @Override
    public ChessGame.TeamColor getTeamColor() {
        return null;
    }

    @Override
    public PieceType getPieceType() {
        return null;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}

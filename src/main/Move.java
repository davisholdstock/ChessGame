import chess.*;

public class Move implements ChessMove {
    @Override
    public ChessPosition getStartPosition() {
        return null;
    }

    @Override
    public ChessPosition getEndPosition() {
        return null;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return null;
    }

    private boolean PawnMove(ChessPiece piece, ChessPosition start, ChessPosition end) {
        if (piece.getPieceType().equals(ChessGame.TeamColor.WHITE) && ((start.getRow() + 1) == (end.getRow())) && end.getRow() < 8) {
            return true;
        } else if (piece.getPieceType().equals(ChessGame.TeamColor.BLACK) && ((start.getRow() - 1) == (end.getRow()))) {
            return true;
        }
        return false;
    }

    private boolean RookMove() {return false;}

    private boolean BishopMove() {return false;}

    private boolean KnightMove() {return false;}

    private boolean QueenMove() {return false;}

    private boolean KingMove() {return false;}
}

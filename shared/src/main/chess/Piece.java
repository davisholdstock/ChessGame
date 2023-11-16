package chess;

import java.util.Collection;

public class Piece implements ChessPiece {
    public ChessGame.TeamColor color;
    public PieceType type;

    public Piece(ChessGame.TeamColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (getPieceType()) {
            case PAWN -> {
                return Game.rules.pawnMoves(board, myPosition);
            }
            case ROOK -> {
                return Game.rules.rookMoves(board, myPosition);
            }
            case BISHOP -> {
                return Game.rules.bishopMoves(board, myPosition);
            }
            case KNIGHT -> {
                return Game.rules.knightMoves(board, myPosition);
            }
            case KING -> {
                return Game.rules.kingMoves(board, myPosition);
            }
            case QUEEN -> {
                return Game.rules.queenMoves(board, myPosition);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        switch (getPieceType()) {
            case PAWN -> {
                return (color == ChessGame.TeamColor.WHITE) ? "P" : "p";
            }
            case ROOK -> {
                return (color == ChessGame.TeamColor.WHITE) ? "R" : "r";
            }
            case BISHOP -> {
                return (color == ChessGame.TeamColor.WHITE) ? "B" : "b";
            }
            case KNIGHT -> {
                return (color == ChessGame.TeamColor.WHITE) ? "N" : "n";
            }
            case KING -> {
                return (color == ChessGame.TeamColor.WHITE) ? "K" : "k";
            }
            case QUEEN -> {
                return (color == ChessGame.TeamColor.WHITE) ? "Q" : "q";
            }
            default -> {
                return "?";
            }
        }
    }
}

package main;

import chess.*;

import java.util.Collection;

public class Piece implements ChessPiece {
    public ChessGame.TeamColor color;
    public PieceType type;
    private Rules rules;

    public Piece(ChessGame.TeamColor color, PieceType type) {
        this.color = color;
        this.type = type;
        rules = new Rules();
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    public void setPieceType(PieceType type) {
        this.type = type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (getPieceType()) {
            case PAWN -> {
                return rules.pawnMoves(board, myPosition);
            }
            case ROOK -> {
                return rules.rookMoves(board, myPosition);
            }
            case BISHOP -> {
                return rules.bishopMoves(board, myPosition);
            }
            case KNIGHT -> {
                return rules.knightMoves(board, myPosition);
            }
            case KING -> {
                return rules.kingMoves(board, myPosition);
            }
            case QUEEN -> {
                return rules.queenMoves(board, myPosition);
            }
            default -> {
                return null;
            }
        }
    }
}

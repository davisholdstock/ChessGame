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
////            case ROOK -> {
////                validMoves = rookMoves(startPosition);
//            // FIXME add Rook
////            }
////            case BISHOP -> {
////                validMoves = bishopMoves(startPosition);
//            // FIXME add Bishop
////            }
////            case KNIGHT -> {
////                validMoves = knightMoves(startPosition);
//            // FIXME add Knight
////            }
            case KING -> {
                return rules.kingMoves(board, myPosition);
//            // FIXME add King
            }
////            case QUEEN -> {
////                validMoves = queenMoves(startPosition);
//            // FIXME add Queen
////            }
            default -> {
                return null;
            }
        }
    }
}

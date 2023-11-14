package main;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Objects;

public class Move extends Game implements ChessMove {
    ChessPiece.PieceType promotionPiece;
    ChessPiece piece;
    ChessPosition start;
    ChessPosition end;

    public Move(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionPiece) {
        this.promotionPiece = promotionPiece;
        this.start = start;
        this.end = end;
    }

    @Override
    public ChessPosition getStartPosition() {
        return start;
    }

    @Override
    public ChessPosition getEndPosition() {
        return end;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return promotionPiece == move.promotionPiece
                && start.getRow() == move.start.getRow()
                && start.getColumn() == move.start.getColumn()
                && end.getRow() == move.end.getRow()
                && end.getColumn() == move.end.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotionPiece, piece, start, end);
    }
}

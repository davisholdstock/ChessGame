package main;

import chess.ChessGame;
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
        this.piece = getGame().getBoard().getPiece(start);
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
        return promotionPiece == move.promotionPiece && start.equals(move.start) && end.equals(move.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotionPiece, piece, start, end);
    }

    /////////////////////////////////////////////////////////////////////
    private boolean PawnMove() {
        // FIXME
        if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)
                && ((start.getRow() + 1) == (end.getRow()))
                && start.getRow() >= 1
                && end.getRow() <= 8) {
            return true;
        } else if (piece.getTeamColor().equals(ChessGame.TeamColor.BLACK)
                && ((start.getRow() - 1) == (end.getRow()))
                && start.getRow() <= 8
                && end.getRow() >= 1) {
            return true;
        }
        return false;
    }

    private boolean RookMove() {
        if (isOnBoard(start) && isOnBoard(end)) {
            for (int i = start.getRow(); i <= getGame().getBoard().getRows(); ++i) {
                if (moveForward(i)) return true;
            }

            for (int i = start.getRow(); i >= 1; --i) {
                if (moveBackward(i)) return true;
            }

            for (int i = start.getColumn(); i <= getGame().getBoard().getColumns(); ++i) {
                if (moveRight(i)) return true;
            }

            for (int i = start.getRow(); i >= 1; --i) {
                if (moveLeft(i)) return true;
            }
        }
        return false;
    }

    private boolean BishopMove() {
        if (isOnBoard(start) && isOnBoard(end)) {
            for (int i = Math.max(start.getRow(), start.getColumn()); i <= getGame().getBoard().getRows(); ++i) {
                if (moveForwardRight(i)) return true;
            }
            for (int i = Math.min(start.getRow(), start.getColumn()); i >= 1; --i) {
                if (moveForwardLeft(i)) return true;
            }
            for (int i = Math.min(start.getRow(), start.getColumn()); i >= 1; --i) {
                if (moveBackwardRight(i)) return true;
            }
            for (int i = Math.min(start.getRow(), start.getColumn()); i >= 1; --i) {
                if (moveBackwardLeft(i)) return true;
            }

            // FIXME add backward right and backward left

            for (int i = start.getRow(); i >= 1; --i) {
                if (moveBackward(i)) return true;
            }
        }
        return false;
    }

    private boolean KnightMove() {return false;}

    private boolean QueenMove() {
        if (isOnBoard(start) && isOnBoard(end)) {

        }
        return false;
    }

    private boolean KingMove() {
        if (isOnBoard(start) && isOnBoard(end)) {
            if (moveRight(1)) return true;
            if (moveLeft(1)) return true;
            if (moveForward(1)) return true;
            if (moveBackward(1)) return true;
            if (moveForwardRight(1)) return true;
            if (moveForwardLeft(1)) return true;
            if (moveBackwardRight(1)) return true;
            if (moveBackwardLeft(1)) return true;
            return false;
        }
        return false;
    }

    private boolean isOnBoard (ChessPosition position) {
        if (position.getRow() >= 1
                && position.getRow() <= getGame().getBoard().getRows()
                && position.getColumn() >= 1
                && position.getColumn() <= getGame().getBoard().getColumns())
            return true;
        return false;
    }

    private boolean moveForward(int numSquares) {
        return (start.getColumn() == end.getColumn() && (start.getRow() + numSquares) == end.getRow());
    }

    private boolean moveBackward(int numSquares) {
        return (start.getColumn() == end.getColumn() && (start.getRow() - numSquares) == end.getRow());
    }

    private boolean moveRight(int numSquares) {
        return (start.getRow() == end.getRow() && (start.getColumn() + numSquares) == end.getColumn());
    }

    private boolean moveLeft(int numSquares) {
        return (start.getRow() == end.getRow() && (start.getColumn() - numSquares) == end.getColumn());
    }

    private boolean moveForwardRight(int numSquares) {
        return ((start.getColumn() + numSquares) == end.getColumn() && (start.getRow() + numSquares) == end.getRow());
    }

    private boolean moveForwardLeft(int numSquares) {
        return ((start.getColumn() - numSquares) == end.getColumn() && (start.getRow() + numSquares) == end.getRow());
    }

    private boolean moveBackwardRight(int numSquares) {
        return ((start.getColumn() + numSquares) == end.getColumn() && (start.getRow() - numSquares) == end.getRow());
    }

    private boolean moveBackwardLeft(int numSquares) {
        return ((start.getColumn() - numSquares) == end.getColumn() && (start.getRow() - numSquares) == end.getRow());
    }
}

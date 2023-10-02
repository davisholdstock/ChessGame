package main;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class Piece extends Game implements ChessPiece {
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
        return pawnMoves(board, myPosition);
    }

    public ArrayList<ChessMove> pawnMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        if (isOnBoard(startPosition)) { // Move two squares at fist
            if (PawnOnStartingSquare(startPosition, board.getPiece(startPosition).getTeamColor())) {
                for (int i = 1; i <= 2; ++i) {
                    if (moveForward(i, startPosition) != null)
                        moves.add(moveForward(i, startPosition));
                    else break;
                }
            }
            else { // Move One square
                if (board.getPiece(startPosition).getTeamColor().equals(TeamColor.WHITE)) {
                    if (board.getPiece(new Position((startPosition.getRow() + 2), startPosition.getColumn() + 1)) == null)
                        moves.add(new Move(startPosition, new Position((startPosition.getRow() + 2), startPosition.getColumn() + 1), null));
                }
                else {
                    if (board.getPiece(new Position((startPosition.getRow() - 2), startPosition.getColumn())) != null)
                        moves.add(new Move(startPosition, new Position((startPosition.getRow() - 2), startPosition.getColumn()), null));
                }
            }
        }

        // FIXME can't en passant, promote, or capture

        return moves;
    }

    private ChessMove moveForward(int numSquares, ChessPosition position) {
        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
            if (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) != null
                    && isOnBoard(new Position((position.getRow() + numSquares), position.getColumn()))) {
                return new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), null);
            }
            return null;
        }
        if (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())) != null
                && isOnBoard(new Position((position.getRow() - numSquares), position.getColumn())))
            return new Move(position, new Position((position.getRow() - numSquares), position.getColumn()), null);
        return null;
    }

    private boolean isOnBoard (ChessPosition position) {
        return (position.getRow() >= 1
                && position.getRow() <= getGame().getBoard().getRows()
                && position.getColumn() >= 1
                && position.getColumn() <= getGame().getBoard().getColumns());
    }
}

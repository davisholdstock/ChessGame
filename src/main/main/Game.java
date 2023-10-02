package main;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class Game implements ChessGame {
    TeamColor turn;
    ChessBoard board;

    public Game() {
        turn = TeamColor.WHITE;
        board = new Board();
    }

    public Game getGame() {
        return this;
    }

    @Override
    public TeamColor getTeamTurn() {
        return turn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        turn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {

        ArrayList<ChessMove> validMoves = new ArrayList<>();
        switch (board.getPiece(startPosition).getPieceType()) {
            case PAWN -> {
                validMoves = pawnMoves(startPosition);
            }
            case ROOK -> {
                validMoves = rookMoves(startPosition);
            }
            case BISHOP -> {
                validMoves = bishopMoves(startPosition);
            }
            case KNIGHT -> {
                validMoves = knightMoves(startPosition);
            }
            case KING -> {
                validMoves = kingMoves(startPosition);
            }
            case QUEEN -> {
                validMoves = queenMoves(startPosition);
            }
            default -> {
                validMoves = null;
            }
        }

        return validMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        for (ChessMove move1: moves) {
            if (move1 == move) {
                board.movePiece(move.getStartPosition(), move.getEndPosition());
                return;
            }
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        // FIXME
        //if (teamColor == board.findKing(teamColor)) {
            //return true;
        //}
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        // FIXME
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        // FIXME
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }

    public ArrayList<ChessMove> pawnMoves(ChessPosition startPosition) {
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
                if (board.getPiece(startPosition).getTeamColor().equals(TeamColor.WHITE))
                    if (board.getPiece(new Position((startPosition.getRow() + 1), startPosition.getColumn())) != null)
                        moves.add(new Move(startPosition, new Position((startPosition.getRow() + 1), startPosition.getColumn()), null));
                else
                    if (board.getPiece(new Position((startPosition.getRow() - 1), startPosition.getColumn())) != null)
                        moves.add(new Move(startPosition, new Position((startPosition.getRow() - 1), startPosition.getColumn()), null));
            }
        }

        // FIXME can't en passant, promote, or capture

        return moves;
    }

    public ArrayList<ChessMove> rookMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }

    public ArrayList<ChessMove> bishopMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }

    public ArrayList<ChessMove> knightMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }

    public ArrayList<ChessMove> kingMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

//        if (isOnBoard(start) && isOnBoard(end)) {
//            if (moveRight(1)) return true;
//            if (moveLeft(1)) return true;
//            if (moveForward(1)) return true;
//            if (moveBackward(1)) return true;
//            if (moveForwardRight(1)) return true;
//            if (moveForwardLeft(1)) return true;
//            if (moveBackwardRight(1)) return true;
//            if (moveBackwardLeft(1)) return true;
//            return false;
//        }
//        return false;

        return moves;
    }

    public ArrayList<ChessMove> queenMoves(ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }

    private boolean isOnBoard (ChessPosition position) {
        return (position.getRow() >= 1
                && position.getRow() <= getGame().getBoard().getRows()
                && position.getColumn() >= 1
                && position.getColumn() <= getGame().getBoard().getColumns());
    }

    public boolean PawnOnStartingSquare(ChessPosition position, TeamColor color) {
        if (color.equals(TeamColor.WHITE))
            return (position.getRow() == 1);
        return (position.getRow() == 7);
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

    private ChessMove moveBackward(int numSquares, ChessPosition position) {
        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
            if (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())) != null
                    && isOnBoard(new Position((position.getRow() - numSquares), position.getColumn()))) {
                return new Move(position, new Position((position.getRow() - numSquares), position.getColumn()), null);
            }
            return null;
        }
        if (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) != null
                && isOnBoard(new Position((position.getRow() + numSquares), position.getColumn())))
            return new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), null);
        return null;
    }

    private ChessMove moveRight(int numSquares, ChessPosition position) {
        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
            if (board.getPiece(new Position((position.getRow()), position.getColumn() + numSquares)) != null
                    && isOnBoard(new Position((position.getRow()), position.getColumn() + numSquares))) {
                return new Move(position, new Position((position.getRow()), position.getColumn() + numSquares), null);
            }
            return null;
        }
        if (board.getPiece(new Position((position.getRow()), position.getColumn() - numSquares)) != null
                && isOnBoard(new Position((position.getRow()), position.getColumn() - numSquares)))
            return new Move(position, new Position((position.getRow()), position.getColumn() - numSquares), null);
        return null;
    }

    private ChessMove moveLeft(int numSquares, ChessPosition position) {
        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
            if (board.getPiece(new Position((position.getRow()), position.getColumn() - numSquares)) != null
                    && isOnBoard(new Position((position.getRow()), position.getColumn() - numSquares))) {
                return new Move(position, new Position((position.getRow()), position.getColumn() - numSquares), null);
            }
            return null;
        }
        if (board.getPiece(new Position((position.getRow()), position.getColumn() + numSquares)) != null
                && isOnBoard(new Position((position.getRow()), position.getColumn() + numSquares)))
            return new Move(position, new Position((position.getRow()), position.getColumn() + numSquares), null);
        return null;
    }

    private ChessMove moveForwardRight(int numSquares, ChessPosition position) {
        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
            if (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn() + numSquares)) != null
                    && isOnBoard(new Position((position.getRow() + numSquares), position.getColumn() + numSquares))) {
                return new Move(position, new Position((position.getRow() + numSquares), position.getColumn() + numSquares), null);
            }
            return null;
        }
        if (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn() - numSquares)) != null
                && isOnBoard(new Position((position.getRow() - numSquares), position.getColumn() - numSquares)))
            return new Move(position, new Position((position.getRow() - numSquares), position.getColumn() - numSquares), null);
        return null;
    }

    private ChessMove moveForwardLeft(int numSquares, ChessPosition position) {
        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
            if (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn() - numSquares)) != null
                    && isOnBoard(new Position((position.getRow() + numSquares), position.getColumn() - numSquares))) {
                return new Move(position, new Position((position.getRow() + numSquares), position.getColumn() - numSquares), null);
            }
            return null;
        }
        if (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn() + numSquares)) != null
                && isOnBoard(new Position((position.getRow() - numSquares), position.getColumn() + numSquares)))
            return new Move(position, new Position((position.getRow() - numSquares), position.getColumn() + numSquares), null);
        return null;
    }
//
//    private boolean moveBackwardRight(int numSquares) {
//        return ((start.getColumn() + numSquares) == end.getColumn() && (start.getRow() - numSquares) == end.getRow());
//    }
//
//    private boolean moveBackwardLeft(int numSquares) {
//        return ((start.getColumn() - numSquares) == end.getColumn() && (start.getRow() - numSquares) == end.getRow());
//    }
}


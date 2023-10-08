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
        return board.getPiece(startPosition).pieceMoves(board, startPosition);
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves;
        ChessBoard previousBoard = new Board(board);
        if (board.getPiece(move.getStartPosition()) != null) {
            moves = validMoves(move.getStartPosition());

            for (ChessMove move1 : moves) {
                if (move1.equals(move)) {
                    board.movePiece(move.getStartPosition(), move.getEndPosition());
                    return;
                }
            }
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessMove> otherPieceMoves = new ArrayList<ChessMove>();
        for (int i = 0; i < board.getRows(); ++i) {
            for (int j = 0; j < board.getColumns(); ++j) {
                if (board.getPiece(new Position(i, j)) != null
                        && board.getPiece(new Position(i, j)).getTeamColor() != teamColor) {
                    otherPieceMoves.addAll(board.getPiece(new Position(i, j)).pieceMoves(board, new Position(i, j)));
                }
            }
        }
        for (ChessMove otherPieceMove : otherPieceMoves) {
            if (otherPieceMove.getEndPosition().equals(board.findKing(teamColor)))
                return true;
        }
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        // FIXME
        Collection<ChessMove> validKingMoves = new ArrayList<ChessMove>();
        if (isInCheck(teamColor)) {
            validKingMoves = board.getPiece(board.findKing(teamColor)).pieceMoves(board, board.findKing(teamColor));
        }
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

    @Override
    public String toString() {
        return board.toString();
    }
}


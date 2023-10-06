package main;

import chess.*;

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

    @Override
    public String toString() {
        return board.toString();
    }
}


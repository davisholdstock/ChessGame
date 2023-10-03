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

        Collection<ChessMove> validMoves = new ArrayList<>();
        switch (board.getPiece(startPosition).getPieceType()) {
            case PAWN -> {
                validMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
            }
//            case ROOK -> {
//                validMoves = rookMoves(startPosition);
                // FIXME add Rook
//            }
//            case BISHOP -> {
//                validMoves = bishopMoves(startPosition);
                // FIXME add Bishop
//            }
//            case KNIGHT -> {
//                validMoves = knightMoves(startPosition);
                // FIXME add Knight
//            }
//            case KING -> {
//                validMoves = kingMoves(startPosition);
                // FIXME add King
//            }
//            case QUEEN -> {
//                validMoves = queenMoves(startPosition);
                // FIXME add Queen
//            }
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
}


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

    public Game(ChessGame otherGame) {
        turn = otherGame.getTeamTurn();
        board = otherGame.getBoard();
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
        Collection<ChessMove> moves = new ArrayList<>();
        Collection<ChessMove> movesReturned = board.getPiece(startPosition).pieceMoves(board, startPosition);

        for (ChessMove move : movesReturned) {
            ChessBoard tempBoard = new Board(getBoard());
            tempBoard.movePiece(move.getStartPosition(), move.getEndPosition());
            if (!tempBoard.isInCheck(board.getPiece(startPosition).getTeamColor())) {
                moves.add(move);
            }
        }
        return moves;

    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves;
        ChessBoard previousBoard = board;
        if (board.getPiece(move.getStartPosition()) != null
                && board.getPiece(move.getStartPosition()).getTeamColor() == turn) {
            moves = validMoves(move.getStartPosition());

            for (ChessMove move1 : moves) {
                if (move1.equals(move)) {
                    ChessBoard tempBoard = new Board(getBoard());
                    tempBoard.movePiece(move.getStartPosition(), move.getEndPosition());
                    if (tempBoard.isInCheck(turn)) {
                        throw new InvalidMoveException("Not Moving out of check");
                    }
                    else {
                        if (move.getPromotionPiece() != null)
                            board.movePieceAndPromote(move.getStartPosition(), move.getEndPosition(), move.getPromotionPiece());
                        else
                            board.movePiece(move.getStartPosition(), move.getEndPosition());
                        turn = (turn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
                    }
                    return;
                }
            }
            throw new InvalidMoveException("Invalid Move");
        }
        throw new InvalidMoveException("Invalid Turn");
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return board.isInCheck(teamColor);
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        // Check to see if the king can move and not be in check
        Collection<ChessMove> validKingMoves = new ArrayList<ChessMove>();
        if (isInCheck(teamColor)) {
            boolean kingCanMove = true;
            validKingMoves = board.getPiece(board.findKing(teamColor)).pieceMoves(board, board.findKing(teamColor));
            for (ChessMove move : validKingMoves) {
                ChessBoard tempBoard = new Board(getBoard());
                tempBoard.movePiece(move.getStartPosition(), move.getEndPosition());
                if (!tempBoard.isInCheck(teamColor)) {
                    return false;
                }
            }
            // Check to see if any pieces can block the attack on the king
            ArrayList<ChessMove> validOtherPieceMoves = new ArrayList<ChessMove>();
            for (int i = 0; i < board.getRows(); ++i) {
                for (int j = 0; j < board.getColumns(); ++j) {
                    if (board.getPiece(new Position(i, j)) != null
                            && board.getPiece(new Position(i, j)).getTeamColor() == teamColor) {
                        validOtherPieceMoves.addAll(board.getPiece(new Position(i, j)).pieceMoves(board, new Position(i, j)));
                    }
                }
            }
            for (ChessMove otherPieceMove : validOtherPieceMoves) {
                ChessBoard tempBoard = new Board(getBoard());
                tempBoard.movePiece(otherPieceMove.getStartPosition(), otherPieceMove.getEndPosition());
                if (!tempBoard.isInCheck(teamColor)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        // FIXME
        // Check to see if the king can move and not be in check
        Collection<ChessMove> validKingMoves = new ArrayList<ChessMove>();
        if (!isInCheck(teamColor)) {
            boolean kingCanMove = true;
            validKingMoves = board.getPiece(board.findKing(teamColor)).pieceMoves(board, board.findKing(teamColor));
            for (ChessMove move : validKingMoves) {
                ChessBoard tempBoard = new Board(getBoard());
                tempBoard.movePiece(move.getStartPosition(), move.getEndPosition());
                if (!tempBoard.isInCheck(teamColor)) {
                    return false;
                }
            }
            // Check to see if any pieces can block the attack on the king
            ArrayList<ChessMove> validOtherPieceMoves = new ArrayList<ChessMove>();
            for (int i = 0; i < board.getRows(); ++i) {
                for (int j = 0; j < board.getColumns(); ++j) {
                    if (board.getPiece(new Position(i, j)) != null
                            && board.getPiece(new Position(i, j)).getTeamColor() == teamColor) {
                        validOtherPieceMoves.addAll(board.getPiece(new Position(i, j)).pieceMoves(board, new Position(i, j)));
                    }
                }
            }
            for (ChessMove otherPieceMove : validOtherPieceMoves) {
                ChessBoard tempBoard = new Board(getBoard());
                tempBoard.movePiece(otherPieceMove.getStartPosition(), otherPieceMove.getEndPosition());
                if (!tempBoard.isInCheck(teamColor)) {
                    return false;
                }
            }
            return true;
        }
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


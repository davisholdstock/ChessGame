package main;

import chess.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Game implements ChessGame {
    TeamColor turn;
    ChessBoard board;
    Boolean[] castling;
    Position enPassant;
    int halfMove; // Moves since last pawn move
    int fullMove; // Total moves in the game

    public Game() {
        turn = TeamColor.WHITE;
        board = new Board();
    }

    public Game(ChessGame otherGame) {
        turn = otherGame.getTeamTurn();
        board = otherGame.getBoard();
        castling = new Boolean[4];
        Arrays.fill(castling, true);
        enPassant = null;
        halfMove = 0;
        fullMove = 0;
    }

    public Game(String fenNotation) {
        String[] gameSetup = fenNotation.split("\\s+");
        switch (gameSetup[1]) {
            case "w" -> {
                turn = TeamColor.WHITE;
                break;
            }
            case "b" -> {
                turn = TeamColor.BLACK;
                break;
            }
        }
        castling = new Boolean[4];
        Arrays.fill(castling, false);
        for (int i = 0; i < gameSetup[2].length(); ++i) {
            switch (gameSetup[2].charAt(i)) {
                case 'K' -> {
                    castling[0] = true;
                    break;
                }
                case 'Q' -> {
                    castling[1] = true;
                    break;
                }
                case 'k' -> {
                    castling[2] = true;
                    break;
                }
                case 'q' -> {
                    castling[3] = true;
                    break;
                }
            }
        }
        if (gameSetup[3].equals("-"))
            enPassant = null;
        else
            enPassant = new Position(gameSetup[3]);
        halfMove = Integer.parseInt(gameSetup[4]);
        halfMove = Integer.parseInt(gameSetup[5]);
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
                    } else {
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

    public String fenNotation() {
        StringBuilder chessGame = new StringBuilder();
        chessGame.append(board.fenNotation());
        chessGame.append(" ");
        switch (turn) {
            case WHITE -> {
                chessGame.append("w");
                break;
            }
            case BLACK -> {
                chessGame.append("b");
                break;
            }
            default -> {
                chessGame.append("N");
                break;
            }
        }
        chessGame.append(" ");
        for (int i = 0; i < castling.length; ++i) {
            switch (i) {
                case 0 -> {
                    if (castling[i])
                        chessGame.append("K");
                    break;
                }
                case 1 -> {
                    if (castling[i])
                        chessGame.append("Q");
                    break;
                }
                case 2 -> {
                    if (castling[i])
                        chessGame.append("k");
                    break;
                }
                case 3 -> {
                    if (castling[i])
                        chessGame.append("q");
                    break;
                }
                default -> {
                    break;
                }
            }
        }
        chessGame.append(" ");
        if (enPassant != null)
            chessGame.append(enPassant.toString()); // Replace with the notation of the square behind the pawn that can be taken en Passant
        else
            chessGame.append("-");
        chessGame.append(" ");
        chessGame.append(halfMove);
        chessGame.append(" ");
        chessGame.append(fullMove);
        return null;
    }
}


import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        for (int i = 1; i <= board.getRows(); ++i) {
            for (int j = 1; j <= board.getColumns(); ++j) {
                if (new Move(startPosition, new Position(i,j), null).getEndPosition() != startPosition) {
                    validMoves.add(new Move(startPosition, new Position(i,j), null));
                }
            }
        }
        return validMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        for (ChessMove move1: moves) {
            if (move1 == move) {
                // FIXME make the move
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



    @Test
    @DisplayName("Reset Board")
    public void defaultGameBoard() {
        ChessBoard board = new Board();

        /*
        |r|n|b|q|k|b|n|r|
		|p|p|p|p|p|p|p|p|
		| | | | | | | | |
		| | | | | | | | |
		| | | | | | | | |
		| | | | | | | | |
		|P|P|P|P|P|P|P|P|
		|R|N|B|Q|K|B|N|R|
         */

        //set board to default
        board.resetBoard();

        //back row pieces
        //white
        Assertions.assertEquals(ChessPiece.PieceType.ROOK,
                board.getPiece(new Position(1, 1)).getPieceType(),
                "Piece at rook starting position was not a rook");
        Assertions.assertEquals(ChessPiece.PieceType.KNIGHT,
                board.getPiece(new Position(1, 2)).getPieceType(),
                "Piece at knight starting position was not a knight");
        Assertions.assertEquals(ChessPiece.PieceType.BISHOP,
                board.getPiece(new Position(1, 3)).getPieceType(),
                "Piece at bishop starting position was not a bishop");
        Assertions.assertEquals(ChessPiece.PieceType.QUEEN,
                board.getPiece(new Position(1, 4)).getPieceType(),
                "Piece at queen starting position was not a queen");
        Assertions.assertEquals(ChessPiece.PieceType.KING,
                board.getPiece(new Position(1, 5)).getPieceType(),
                "Piece at king starting position was not a king");
        Assertions.assertEquals(ChessPiece.PieceType.BISHOP,
                board.getPiece(new Position(1, 6)).getPieceType(),
                "Piece at bishop starting position was not a bishop");
        Assertions.assertEquals(ChessPiece.PieceType.KNIGHT,
                board.getPiece(new Position(1, 7)).getPieceType(),
                "Piece at knight starting position was not a knight");
        Assertions.assertEquals(ChessPiece.PieceType.ROOK,
                board.getPiece(new Position(1, 8)).getPieceType(),
                "Piece at rook starting position was not a rook");

        //black
        Assertions.assertEquals(ChessPiece.PieceType.ROOK,
                board.getPiece(new Position(8, 1)).getPieceType(),
                "Piece at rook starting position was not a rook");
        Assertions.assertEquals(ChessPiece.PieceType.KNIGHT,
                board.getPiece(new Position(8, 2)).getPieceType(),
                "Piece at knight starting position was not a knight");
        Assertions.assertEquals(ChessPiece.PieceType.BISHOP,
                board.getPiece(new Position(8, 3)).getPieceType(),
                "Piece at bishop starting position was not a bishop");
        Assertions.assertEquals(ChessPiece.PieceType.QUEEN,
                board.getPiece(new Position(8, 4)).getPieceType(),
                "Piece at queen starting position was not a queen");
        Assertions.assertEquals(ChessPiece.PieceType.KING,
                board.getPiece(new Position(8, 5)).getPieceType(),
                "Piece at king starting position was not a king");
        Assertions.assertEquals(ChessPiece.PieceType.BISHOP,
                board.getPiece(new Position(8, 6)).getPieceType(),
                "Piece at bishop starting position was not a bishop");
        Assertions.assertEquals(ChessPiece.PieceType.KNIGHT,
                board.getPiece(new Position(8, 7)).getPieceType(),
                "Piece at knight starting position was not a knight");
        Assertions.assertEquals(ChessPiece.PieceType.ROOK,
                board.getPiece(new Position(8, 8)).getPieceType(),
                "Piece at rook starting position was not a rook");

        //pawns
        for (int column = 1; column <= 8; ++column) {
            Assertions.assertEquals(ChessPiece.PieceType.PAWN,
                    board.getPiece(new Position(2, column)).getPieceType(),
                    "Piece at pawn starting position was not a pawn");
            Assertions.assertEquals(ChessPiece.PieceType.PAWN,
                    board.getPiece(new Position(7, column)).getPieceType(),
                    "Piece at pawn starting position was not a pawn");
        }

        //check color
        for (int column = 1; column <= 8; ++column) {
            //white team
            Assertions.assertEquals(ChessGame.TeamColor.WHITE,
                    board.getPiece(new Position(1, column)).getTeamColor(),
                    "Piece at starting position was incorrect color");
            Assertions.assertEquals(ChessGame.TeamColor.WHITE,
                    board.getPiece(new Position(2, column)).getTeamColor(),
                    "Piece at starting position was incorrect color");

            //black team
            Assertions.assertEquals(ChessGame.TeamColor.BLACK,
                    board.getPiece(new Position(7, column)).getTeamColor(),
                    "Piece at starting position was incorrect color");
            Assertions.assertEquals(ChessGame.TeamColor.BLACK,
                    board.getPiece(new Position(8, column)).getTeamColor(),
                    "Piece at starting position was incorrect color");
        }
    }
}


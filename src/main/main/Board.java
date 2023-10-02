package main;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class Board implements ChessBoard {
    final int rows = 8;
    final int columns = 8;
    ChessPiece[][] chessBoard;

    public Board() {
        chessBoard = new ChessPiece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoard[position.getRow()][position.getColumn()] = piece;
    }

    public void deletePiece(ChessPosition position) {
        chessBoard[position.getRow()][position.getColumn()] = null;
    }

    @Override
    public void movePiece(ChessPosition start, ChessPosition end) {
        addPiece(end, getPiece(start));
        deletePiece(start);
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard[position.getRow()][position.getColumn()];
    }

    public ChessPosition findKing(ChessGame.TeamColor color) {
        for (int i = 1; i <= rows; ++i) {
            for (int j = 1; j <= columns; ++j) {
                if (getPiece(new Position(i, j)).getPieceType() == ChessPiece.PieceType.KING && getPiece(new Position(i, j)).getTeamColor() == color) {
                    return new Position(i,j);
                }
            }
        }
        return null;
    }

    @Override
    public void resetBoard() {
        for (int i = 0; i < rows; ++i) {
            for (int y = 0; y < columns; ++y) {
                chessBoard[i][y] = null;
            }
        }

        chessBoard = new ChessPiece[][]
                {{new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK)},
                        {new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN)},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null, null},
                        {new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN)},
                        {new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT), new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK)}};
    }
}

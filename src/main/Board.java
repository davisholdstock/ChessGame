import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class Board implements ChessBoard {
    Piece whiteKing, whiteQueen, whiteWhiteBishop, whiteBlackBishop, whiteQueensKnight, whiteKingsKnight, whiteQueensRook, whiteKingsRook,
            whitePawn1, whitePawn2, whitePawn3, whitePawn4, whitePawn5, whitePawn6, whitePawn7, whitePawn8, whitePawn9;
    Piece blackKing, blackQueen, blackWhiteBishop, blackBlackBishop, blackQueensKnight, blackKingsKnight, blackQueensRook, blackKingsRook,
            blackPawn1, blackPawn2, blackPawn3, blackPawn4, blackPawn5, blackPawn6, blackPawn7, blackPawn8, blackPawn9;
    Object[][] chessBoard;

    public Board() {
        chessBoard = new Object[8][8];

        whiteKing = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        blackKing = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        //
        whiteQueen = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        blackQueen = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        //
        whiteWhiteBishop = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        blackWhiteBishop = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        whiteBlackBishop = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        blackBlackBishop = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        //
        whiteQueensKnight = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        blackQueensKnight = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        whiteKingsKnight = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        blackKingsKnight = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        //
        whiteQueensRook = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        blackQueensRook = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        whiteKingsRook = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        blackKingsRook = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        //
        whitePawn1 = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        blackPawn1 = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        whitePawn2 = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        blackPawn2 = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        whitePawn3 = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        blackPawn3 = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        whitePawn4 = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        blackPawn4 = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        whitePawn5 = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        blackPawn5 = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        whitePawn6 = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        blackPawn6 = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        whitePawn7 = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        blackPawn7 = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        whitePawn8 = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        blackPawn8 = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoard[position.getRow()][position.getColumn()] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return null;
    }

    @Override
    public void resetBoard() {

    }
}

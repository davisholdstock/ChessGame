import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Board implements ChessBoard {
    public static final int rows = 8;
    public final int columns = 8;
    ChessPiece[][] chessBoard;

    public Board() {
        chessBoard = new ChessPiece[8][8];
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoard[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard[position.getRow() - 1][position.getColumn() - 1];
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

    @Test
    @DisplayName("Add and Get Piece")
    public void getAddPiece() {
        ChessPosition position = new Position(4, 4);
        ChessPiece piece = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);

        addPiece(position, piece);

        ChessPiece foundPiece = getPiece(position);

        Assertions.assertEquals(piece.getPieceType(), foundPiece.getPieceType(),
                "ChessPiece returned by getPiece had the wrong piece type");
        Assertions.assertEquals(piece.getTeamColor(), foundPiece.getTeamColor(),
                "ChessPiece returned by getPiece had the wrong team color");
    }
}

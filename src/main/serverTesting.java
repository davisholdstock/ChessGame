import chess.ChessBoard;
import main.Board;

public class serverTesting {
    public static void main(String[] args) {
        ChessBoard board = new Board();
        board.resetBoard();
        System.out.println(((Board) board).toFENNotation());
    }
}

package chess;

public class Main {

    public static void main(String[] args) {
        ChessBoard board = new Board();
        board.resetBoard();
        System.out.println(board.toFENNotation());
    }
}

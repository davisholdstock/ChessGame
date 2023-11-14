package ui;

import chess.ChessBoard;
import main.Board;

public class clientMain {
    public static void main(String[] args) {
        ChessBoard board = new Board();
        board.resetBoard();
        System.out.println(board.toFENNotation());
    }
}

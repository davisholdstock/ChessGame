package main;

import chess.ChessBoard;

public class Main {

    public static void main(String[] args) {
        ChessBoard board = new Board();
        board.resetBoard();
        System.out.println(board.toFENNotation());
    }
}

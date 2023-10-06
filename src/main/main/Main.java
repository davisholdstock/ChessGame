package main;

import chess.ChessGame;
import chess.InvalidMoveException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMoveException {
        ChessGame game = new Game();

        game.getBoard().resetBoard();
        System.out.println(game.toString());

        game.makeMove(new Move(new Position(1, 0), new Position(3, 0), null));
        System.out.println(game.toString());

        game.makeMove(new Move(new Position(6, 0), new Position(4, 0), null));
        System.out.println(game.toString());

        game.makeMove(new Move(new Position(3, 0), new Position(4, 0), null));
        System.out.println(game.toString());

    }
}

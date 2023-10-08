package main;

import chess.ChessGame;
import chess.InvalidMoveException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InvalidMoveException {
        ChessGame game = new Game();

        game.getBoard().resetBoard();
        System.out.println(game.toString());

        game.makeMove(new Move(new Position(1, 4), new Position(3, 4), null));
        System.out.println(game.toString());

        game.makeMove(new Move(new Position(6, 5), new Position(4, 5), null));
        System.out.println(game.toString());

        game.makeMove(new Move(new Position(0, 3), new Position(4, 7), null));
        System.out.println(game.toString());

        if (game.isInCheck(ChessGame.TeamColor.BLACK))
            System.out.println("TRUE");

    }
}

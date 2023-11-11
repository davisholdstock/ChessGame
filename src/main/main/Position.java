package main;

import chess.ChessPosition;

import java.util.Objects;

public class Position implements ChessPosition {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position(String squareNotation) {
        switch (squareNotation.charAt(0)) {
            case 'a' -> {
                this.row = 0;
            }
            case 'b' -> {
                this.row = 1;
            }
            case 'c' -> {
                this.row = 2;
            }
            case 'd' -> {
                this.row = 3;
            }
            case 'e' -> {
                this.row = 4;
            }
            case 'f' -> {
                this.row = 5;
            }
            case 'g' -> {
                this.row = 6;
            }
            case 'h' -> {
                this.row = 7;
            }
        }
        this.column = Integer.parseInt(squareNotation.substring(1)) + 1;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        StringBuilder squareNotation = new StringBuilder();
        switch (row) {
            case 0 -> {
                squareNotation.append('a');
            }
            case 1 -> {
                squareNotation.append('b');
            }
            case 2 -> {
                squareNotation.append('c');
            }
            case 3 -> {
                squareNotation.append('d');
            }
            case 4 -> {
                squareNotation.append('e');
            }
            case 5 -> {
                squareNotation.append('f');
            }
            case 6 -> {
                squareNotation.append('g');
            }
            case 7 -> {
                squareNotation.append('h');
            }
        }
        squareNotation.append(column + 1);
        return super.toString();
    }
}

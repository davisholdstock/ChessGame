package chess;

import java.util.ArrayList;

import static chess.EscapeSequences.*;

public class Board implements ChessBoard {
    final int rows = 8;
    final static int rowsStatic = 8;
    final int columns = 8;
    final static int columnsStatic = 8;
    ChessPiece[][] chessBoard;

    public Board() {
        chessBoard = new ChessPiece[rows][columns];
    }

    // Copy constructor
    public Board(ChessBoard otherBoard) {
        // Initialize the new chess board
        this.chessBoard = new ChessPiece[rows][columns];

        // Copy the pieces from the other board
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                ChessPosition position = new Position(i, j);
                ChessPiece piece = otherBoard.getPiece(position);
                if (piece != null) {
                    this.chessBoard[i][j] = new Piece(piece.getTeamColor(), piece.getPieceType());
                } else {
                    this.chessBoard[i][j] = null;
                }
            }
        }
    }

    public Board(String fenNotation) {
        String[] boardSetup = fenNotation.split("/");

        // Initialize the new chess board
        this.chessBoard = new ChessPiece[rows][columns];

        // Copy the pieces from the FEN string
        int blankSpaces = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (boardSetup[i].substring(0, 1).matches("[0-9]")) {
                    this.chessBoard[i][j] = null;
                    if (boardSetup[i].charAt(0) != '0') {
                        boardSetup[i] = (Integer.parseInt(boardSetup[i].substring(0, 1)) - 1) + boardSetup[i].substring(1);
                    } else {
                        boardSetup[i] = boardSetup[i].substring(1);
                    }
                    if ((boardSetup[i].length() > 1) && (boardSetup[i].charAt(0) == '0'))
                        boardSetup[i] = boardSetup[i].substring(1);
                } else {
                    switch (boardSetup[i].charAt(0)) {
                        case 'K' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                        }
                        case 'Q' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                        }
                        case 'R' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                        }
                        case 'B' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                        }
                        case 'N' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                        }
                        case 'P' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                        }
                        case 'k' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                        }
                        case 'q' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                        }
                        case 'r' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                        }
                        case 'b' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                        }
                        case 'n' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                        }
                        case 'p' -> {
                            this.chessBoard[i][j] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                        }
                    }
                    if (boardSetup[i].length() > 1)
                        boardSetup[i] = boardSetup[i].substring(1);
                }
            }
        }
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

    public void movePieceAndPromote(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionPiece) {
        addPiece(end, new Piece(getPiece(start).getTeamColor(), promotionPiece));
        deletePiece(start);
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return chessBoard[position.getRow()][position.getColumn()];
    }

    public ChessPosition findKing(ChessGame.TeamColor color) {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (getPiece(new Position(i, j)) != null
                        && getPiece(new Position(i, j)).getPieceType() == ChessPiece.PieceType.KING
                        && getPiece(new Position(i, j)).getTeamColor() == color) {
                    return new Position(i, j);
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

    @Override
    public String toFENNotation() {
        StringBuilder chessBoard = new StringBuilder();
        int emptySquares = 0;
        for (int i = 0; i < rows; ++i) {
            for (int y = 0; y < columns; ++y) {
                if (getPiece(new Position(i, y)) != null) {
                    if (emptySquares != 0) {
                        chessBoard.append(emptySquares);
                        emptySquares = 0;
                    }
                    chessBoard.append(getPiece(new Position(i, y)).toString());
                } else {
                    ++emptySquares;
                }
            }
            if (emptySquares != 0) {
                chessBoard.append(emptySquares);
                emptySquares = 0;
            }
            if (i != 7)
                chessBoard.append("/");
        }
        return chessBoard.toString();
    }

    @Override
    public String toString() {
        StringBuilder chessBoard = new StringBuilder();
        for (int i = rows - 1; i >= 0; --i) {
            for (int y = 0; y < columns; ++y) {
                if (getPiece(new Position(i, y)) != null)
                    chessBoard.append(getPiece(new Position(i, y)).toString());
                else
                    chessBoard.append("-");
                chessBoard.append(" | ");
            }
            chessBoard.append("\n");
        }
        return chessBoard.toString();
    }

    public void printFancy(ChessGame.TeamColor color) {
        if (color == ChessGame.TeamColor.WHITE) {
            for (int i = rows; i >= -1; --i) {
                for (int y = -1; y <= columns; ++y) {
                    if ((i == rows || i == -1) && y > -1 && y < columns) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(EMPTY);
                        System.out.print(SET_TEXT_FAINT + SET_TEXT_COLOR_WHITE + (char) ('a' + y));
                        System.out.print(EMPTY);
                    } else if ((y == columns || y == -1)) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(EMPTY);
                        if (i < rows && i > -1)
                            System.out.print(SET_TEXT_FAINT + SET_TEXT_COLOR_WHITE + (i + 1));
                        else
                            System.out.print(EMPTY);
                        System.out.print(EMPTY);
                    } else {
                        if ((i % 2 == 0) && (y % 2 != 0) || (i % 2 != 0) && (y % 2 == 0))
                            System.out.print(SET_BG_COLOR_GREEN);
                        else
                            System.out.print(SET_BG_COLOR_WHITE);
                        if (getPiece(new Position(i, y)) != null) {
                            System.out.print(EMPTY);
                            System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + getPiece(new Position(i, y)).toString());
                            System.out.print(EMPTY);
                        } else {
                            System.out.print(EMPTY.repeat(3));
                        }
                    }
                }
                System.out.print(RESET_BG_COLOR);
                System.out.println();
            }
        } else if (color == ChessGame.TeamColor.BLACK) {
            for (int i = -1; i <= rows; ++i) {
                for (int y = columns; y >= -1; --y) {
                    if ((i == rows || i == -1) && y > -1 && y < columns) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(EMPTY);
                        System.out.print(SET_TEXT_FAINT + SET_TEXT_COLOR_WHITE + (char) ('a' + y));
                        System.out.print(EMPTY);
                    } else if (y == columns || y == -1) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(EMPTY);
                        if (i < rows && i > -1)
                            System.out.print(SET_TEXT_FAINT + SET_TEXT_COLOR_WHITE + (i + 1));
                        else
                            System.out.print(EMPTY);
                        System.out.print(EMPTY);
                    } else {
                        if ((i % 2 == 0) && (y % 2 != 0) || (i % 2 != 0) && (y % 2 == 0))
                            System.out.print(SET_BG_COLOR_GREEN);
                        else
                            System.out.print(SET_BG_COLOR_WHITE);
                        if (getPiece(new Position(i, y)) != null) {
                            System.out.print(EMPTY);
                            System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + getPiece(new Position(i, y)));
                            System.out.print(EMPTY);
                        } else {
                            System.out.print(EMPTY.repeat(3));
                        }
                    }
                }
                System.out.print(RESET_BG_COLOR);
                System.out.println();
            }
        } else {
            for (int i = rows; i >= -1; --i) {
                for (int y = -1; y <= columns; ++y) {
                    if ((i == rows || i == -1) && y > -1 && y < columns) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(EMPTY);
                        System.out.print(SET_TEXT_FAINT + SET_TEXT_COLOR_WHITE + (char) ('a' + y));
                        System.out.print(EMPTY);
                    } else if ((y == columns || y == -1)) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(EMPTY);
                        if (i < rows && i > -1)
                            System.out.print(SET_TEXT_FAINT + SET_TEXT_COLOR_WHITE + (i + 1));
                        else
                            System.out.print(EMPTY);
                        System.out.print(EMPTY);
                    } else {
                        if ((i % 2 == 0) && (y % 2 != 0) || (i % 2 != 0) && (y % 2 == 0))
                            System.out.print(SET_BG_COLOR_GREEN);
                        else
                            System.out.print(SET_BG_COLOR_WHITE);
                        if (getPiece(new Position(i, y)) != null) {
                            System.out.print(EMPTY);
                            System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + getPiece(new Position(i, y)).toString());
                            System.out.print(EMPTY);
                        } else {
                            System.out.print(EMPTY.repeat(3));
                        }
                    }
                }
                System.out.print(RESET_BG_COLOR);
                System.out.println();
            }
            System.out.println();
            System.out.println();
            for (int i = -1; i <= rows; ++i) {
                for (int y = columns; y >= -1; --y) {
                    if ((i == rows || i == -1) && y > -1 && y < columns) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(EMPTY);
                        System.out.print(SET_TEXT_FAINT + SET_TEXT_COLOR_WHITE + (char) ('a' + y));
                        System.out.print(EMPTY);
                    } else if (y == columns || y == -1) {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(EMPTY);
                        if (i < rows && i > -1)
                            System.out.print(SET_TEXT_FAINT + SET_TEXT_COLOR_WHITE + (i + 1));
                        else
                            System.out.print(EMPTY);
                        System.out.print(EMPTY);
                    } else {
                        if ((i % 2 == 0) && (y % 2 != 0) || (i % 2 != 0) && (y % 2 == 0))
                            System.out.print(SET_BG_COLOR_GREEN);
                        else
                            System.out.print(SET_BG_COLOR_WHITE);
                        if (getPiece(new Position(i, y)) != null) {
                            System.out.print(EMPTY);
                            System.out.print(SET_TEXT_BOLD + SET_TEXT_COLOR_BLACK + getPiece(new Position(i, y)));
                            System.out.print(EMPTY);
                        } else {
                            System.out.print(EMPTY.repeat(3));
                        }
                    }
                }
                System.out.print(RESET_BG_COLOR);
                System.out.println();
            }
        }
    }

    public boolean isInCheck(ChessGame.TeamColor teamColor) {
        ArrayList<ChessMove> otherPieceMoves = new ArrayList<ChessMove>();
        for (int i = 0; i < getRows(); ++i) {
            for (int j = 0; j < getColumns(); ++j) {
                if (getPiece(new Position(i, j)) != null
                        && getPiece(new Position(i, j)).getTeamColor() != teamColor) {
                    otherPieceMoves.addAll(getPiece(new Position(i, j)).pieceMoves(this, new Position(i, j)));
                }
            }
        }
        for (ChessMove otherPieceMove : otherPieceMoves) {
            if (otherPieceMove.getEndPosition().equals(findKing(teamColor)))
                return true;
        }
        return false;
    }
}

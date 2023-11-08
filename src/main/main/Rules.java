package main;

import chess.*;

import java.util.ArrayList;

public class Rules {

    public Rules() {
    }

    public ArrayList<ChessMove> pawnMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        if (isOnBoard(startPosition)) {
//            checkForPawnCapture(board, startPosition, moves);
            if (PawnOnStartingSquare(startPosition, board.getPiece(startPosition).getTeamColor())) { // Move two squares at fist
                checkForPawnCapture(board, startPosition, moves, null); // Capturing
                for (int i = 1; i <= 2; ++i) {
                    if (moveForward(i, startPosition, board) != null) {
                        moves.add(moveForward(i, startPosition, board));
                    } else break;
                }
            } else if (pawnCanPromote(startPosition, board.getPiece(startPosition).getTeamColor())) { // Move one square and Promote
                //checkForPawnCapture(board, startPosition, moves, null); // Capturing
                for (var type : ChessPiece.PieceType.values()) {
                    if (type != ChessPiece.PieceType.PAWN && type != ChessPiece.PieceType.KING)
                        checkForPawnCapture(board, startPosition, moves, type); // Capturing and Promotion
                    if (moveForwardAndPromote(1, startPosition, board, type) != null && type != ChessPiece.PieceType.PAWN && type != ChessPiece.PieceType.KING)
                        moves.add(moveForwardAndPromote(1, startPosition, board, type));
                }
            } else { // Move One square
                checkForPawnCapture(board, startPosition, moves, null); // Capturing
                if (moveForward(1, startPosition, board) != null)
                    moves.add(moveForward(1, startPosition, board));
            }
        }

        // FIXME can't en passant

        return moves;
    }

    public ArrayList<ChessMove> kingMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        if (moveForward(1, startPosition, board) != null) {
            moves.add(moveForward(1, startPosition, board));
        }
        if (moveBackward(1, startPosition, board) != null) {
            moves.add(moveBackward(1, startPosition, board));
        }
        if (moveLeft(1, startPosition, board) != null) {
            moves.add(moveLeft(1, startPosition, board));
        }
        if (moveRight(1, startPosition, board) != null) {
            moves.add(moveRight(1, startPosition, board));
        }
        if (moveForwardRight(1, startPosition, board) != null) {
            moves.add(moveForwardRight(1, startPosition, board));
        }
        if (moveForwardLeft(1, startPosition, board) != null) {
            moves.add(moveForwardLeft(1, startPosition, board));
        }
        if (moveBackwardRight(1, startPosition, board) != null) {
            moves.add(moveBackwardRight(1, startPosition, board));
        }
        if (moveBackwardLeft(1, startPosition, board) != null) {
            moves.add(moveBackwardLeft(1, startPosition, board));
        }

        // FIXME can't castle

        return moves;
    }

    public ArrayList<ChessMove> rookMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int spacesForward = ((board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ? board.getRows() - startPosition.getRow() : startPosition.getRow());
        int spacesRight = ((board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ? board.getColumns() - startPosition.getColumn() : startPosition.getColumn());
        int spacesBackward = ((board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ? startPosition.getRow() : board.getRows() - startPosition.getRow());
        int spacesLeft = ((board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ? startPosition.getColumn() : board.getColumns() - startPosition.getColumn());

        for (int i = 1; i <= spacesForward; ++i) {
            if (moveForward(i, startPosition, board) != null) {
                moves.add(moveForward(i, startPosition, board));
                if (board.getPiece(moveForward(i, startPosition, board).getEndPosition()) != null)
                    break;
            } else break;
        }

        for (int i = 1; i <= spacesBackward; ++i) {
            if (moveBackward(i, startPosition, board) != null) {
                moves.add(moveBackward(i, startPosition, board));
                if (board.getPiece(moveBackward(i, startPosition, board).getEndPosition()) != null)
                    break;
            } else break;
        }

        for (int i = 1; i <= spacesRight; ++i) {
            if (moveRight(i, startPosition, board) != null) {
                moves.add(moveRight(i, startPosition, board));
                if (board.getPiece(moveRight(i, startPosition, board).getEndPosition()) != null)
                    break;
            } else break;
        }

        for (int i = 1; i <= spacesLeft; ++i) {
            if (moveLeft(i, startPosition, board) != null) {
                moves.add(moveLeft(i, startPosition, board));
                if (board.getPiece(moveLeft(i, startPosition, board).getEndPosition()) != null)
                    break;
            } else break;
        }

        return moves;
    }

    public ArrayList<ChessMove> bishopMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int spacesForwardRight = ((board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ? Math.min(((board.getRows() - 1) - startPosition.getRow()), ((board.getColumns() - 1) - startPosition.getColumn())) : Math.min(startPosition.getColumn(), startPosition.getRow()));
        int spacesForwardLeft = ((board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ? Math.min(startPosition.getColumn(), ((board.getRows() - 1) - startPosition.getRow())) : Math.min(startPosition.getRow(), ((board.getColumns() - 1) - startPosition.getColumn())));
        int spacesBackwardRight = ((board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ? Math.min(startPosition.getRow(), ((board.getColumns() - 1) - startPosition.getColumn())) : Math.min(startPosition.getColumn(), ((board.getRows() - 1) - startPosition.getRow())));
        int spacesBackwardLeft = ((board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) ? Math.min(startPosition.getColumn(), startPosition.getRow()) : Math.min(((board.getRows() - 1) - startPosition.getRow()), ((board.getColumns() - 1) - startPosition.getColumn())));

        for (int i = 1; i <= spacesForwardRight; ++i) {
            if (moveForwardRight(i, startPosition, board) != null) {
                moves.add(moveForwardRight(i, startPosition, board));
                if (board.getPiece(moveForwardRight(i, startPosition, board).getEndPosition()) != null) {
                    break;
                }
            } else break;
        }

        for (int i = 1; i <= spacesForwardLeft; ++i) {
            if (moveForwardLeft(i, startPosition, board) != null) {
                moves.add(moveForwardLeft(i, startPosition, board));
                if (board.getPiece(moveForwardLeft(i, startPosition, board).getEndPosition()) != null) {
                    break;
                }
            } else break;
        }

        for (int i = 1; i <= spacesBackwardRight; ++i) {
            if (moveBackwardRight(i, startPosition, board) != null) {
                moves.add(moveBackwardRight(i, startPosition, board));
                if (board.getPiece(moveBackwardRight(i, startPosition, board).getEndPosition()) != null) {
                    break;
                }
            } else break;
        }

        for (int i = 1; i <= spacesBackwardLeft; ++i) {
            if (moveBackwardLeft(i, startPosition, board) != null) {
                moves.add(moveBackwardLeft(i, startPosition, board));
                if (board.getPiece(moveBackwardLeft(i, startPosition, board).getEndPosition()) != null)
                    break;
            } else break;
        }

        return moves;
    }

    public ArrayList<ChessMove> queenMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        moves.addAll(rookMoves(board, startPosition));
        moves.addAll(bishopMoves(board, startPosition));
        return moves;
    }

    public ArrayList<ChessMove> knightMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        if (moveLikeAKnight(2, 1, startPosition, board) != null) {
            moves.add(moveLikeAKnight(2, 1, startPosition, board));
        }
        if (moveLikeAKnight(2, -1, startPosition, board) != null) {
            moves.add(moveLikeAKnight(2, -1, startPosition, board));
        }
        if (moveLikeAKnight(-2, 1, startPosition, board) != null) {
            moves.add(moveLikeAKnight(-2, 1, startPosition, board));
        }
        if (moveLikeAKnight(-2, -1, startPosition, board) != null) {
            moves.add(moveLikeAKnight(-2, -1, startPosition, board));
        }
        if (moveLikeAKnight(1, 2, startPosition, board) != null) {
            moves.add(moveLikeAKnight(1, 2, startPosition, board));
        }
        if (moveLikeAKnight(-1, 2, startPosition, board) != null) {
            moves.add(moveLikeAKnight(-1, 2, startPosition, board));
        }
        if (moveLikeAKnight(1, -2, startPosition, board) != null) {
            moves.add(moveLikeAKnight(1, -2, startPosition, board));
        }
        if (moveLikeAKnight(-1, -2, startPosition, board) != null) {
            moves.add(moveLikeAKnight(-1, -2, startPosition, board));
        }
        return moves;
    }

    private ChessMove moveForward(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), position.getColumn()))
                    && (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) == null
                    || (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())).getTeamColor() == ChessGame.TeamColor.BLACK  // Can Capture opposite color
                    && board.getPiece(position).getPieceType() != ChessPiece.PieceType.PAWN))) // Can't capture forward if it is a pawn
                return (new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), position.getColumn()))
                    && (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())) == null
                    || (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())).getTeamColor() == ChessGame.TeamColor.WHITE // Can Capture opposite color
                    && board.getPiece(position).getPieceType() != ChessPiece.PieceType.PAWN))) // Can't capture forward if it is a pawn
                return (new Move(position, new Position((position.getRow() - numSquares), position.getColumn()), null));
            return null;
        }
        return null;
    }

    private ChessMove moveForwardAndPromote(int numSquares, ChessPosition position, ChessBoard board, ChessPiece.PieceType promotionPiece) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), position.getColumn()))
                    && board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) == null)
                return (new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), promotionPiece));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), position.getColumn()))
                    && board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())) == null)
                return (new Move(position, new Position((position.getRow() - numSquares), position.getColumn()), promotionPiece));
            return null;
        }
        return null;
    }

    private ChessMove moveBackward(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), position.getColumn()))
                    && (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())).getTeamColor() == ChessGame.TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() - numSquares), position.getColumn()), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), position.getColumn()))
                    && (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())).getTeamColor() == ChessGame.TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), null));
            return null;
        }
        return null;
    }

    private ChessMove moveRight(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position(position.getRow(), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position(position.getRow(), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position(position.getRow(), (position.getColumn() + numSquares))).getTeamColor() == ChessGame.TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position(position.getRow(), (position.getColumn() + numSquares)), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position(position.getRow(), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position(position.getRow(), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position(position.getRow(), (position.getColumn() - numSquares))).getTeamColor() == ChessGame.TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position(position.getRow(), (position.getColumn() - numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveLeft(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position(position.getRow(), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position(position.getRow(), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position(position.getRow(), (position.getColumn() - numSquares))).getTeamColor() == ChessGame.TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position(position.getRow(), (position.getColumn() - numSquares)), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position(position.getRow(), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position(position.getRow(), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position(position.getRow(), (position.getColumn() + numSquares))).getTeamColor() == ChessGame.TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position(position.getRow(), (position.getColumn() + numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveForwardRight(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares))).getTeamColor() == ChessGame.TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() + numSquares), (position.getColumn() + numSquares)), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares))).getTeamColor() == ChessGame.TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() - numSquares), (position.getColumn() - numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveForwardLeft(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares))).getTeamColor() == ChessGame.TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() + numSquares), (position.getColumn() - numSquares)), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares))).getTeamColor() == ChessGame.TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() - numSquares), (position.getColumn() + numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveBackwardRight(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares))).getTeamColor() == ChessGame.TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() - numSquares), (position.getColumn() + numSquares)), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares))).getTeamColor() == ChessGame.TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() + numSquares), (position.getColumn() - numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveBackwardLeft(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares))).getTeamColor() == ChessGame.TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() - numSquares), (position.getColumn() - numSquares)), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares))).getTeamColor() == ChessGame.TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() + numSquares), (position.getColumn() + numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveLikeAKnight(int numSquaresForward, int numSquaresRight, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() + numSquaresForward), (position.getColumn() + numSquaresRight)))
                    && (board.getPiece(new Position((position.getRow() + numSquaresForward), (position.getColumn() + numSquaresRight))) == null
                    || (board.getPiece(new Position((position.getRow() + numSquaresForward), (position.getColumn() + numSquaresRight))).getTeamColor() == ChessGame.TeamColor.BLACK  // Can Capture opposite color
                    && board.getPiece(position).getPieceType() != ChessPiece.PieceType.PAWN))) // Can't capture forward if it is a pawn
                return (new Move(position, new Position((position.getRow() + numSquaresForward), (position.getColumn() + numSquaresRight)), null));
            return null;
        } else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() - numSquaresForward), (position.getColumn() - numSquaresRight)))
                    && (board.getPiece(new Position((position.getRow() - numSquaresForward), (position.getColumn() - numSquaresRight))) == null
                    || (board.getPiece(new Position((position.getRow() - numSquaresForward), (position.getColumn() - numSquaresRight))).getTeamColor() == ChessGame.TeamColor.WHITE // Can Capture opposite color
                    && board.getPiece(position).getPieceType() != ChessPiece.PieceType.PAWN))) // Can't capture forward if it is a pawn
                return (new Move(position, new Position((position.getRow() - numSquaresForward), (position.getColumn() - numSquaresRight)), null));
            return null;
        }
        return null;
    }

    private boolean isOnBoard(ChessPosition position) {
        return (position.getRow() >= 0
                && position.getRow() <= (Board.rowsStatic - 1)
                && position.getColumn() >= 0
                && position.getColumn() <= (Board.columnsStatic - 1));
    }

    public boolean PawnOnStartingSquare(ChessPosition position, ChessGame.TeamColor color) {
        if (color.equals(ChessGame.TeamColor.WHITE))
            return (position.getRow() == 1);
        return (position.getRow() == 6);
    }

    public boolean pawnCanPromote(ChessPosition position, ChessGame.TeamColor color) {
        if (color.equals(ChessGame.TeamColor.WHITE))
            return (position.getRow() == 6);
        return (position.getRow() == 1);
    }

    public ArrayList<ChessMove> checkForPawnCapture(ChessBoard board, ChessPosition position, ArrayList<ChessMove> moves, ChessPiece.PieceType promotionPiece) {
        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (isOnBoard(new Position((position.getRow() + 1), (position.getColumn() + 1)))
                    && board.getPiece(new Position((position.getRow() + 1), (position.getColumn() + 1))) != null) {
                if (board.getPiece(new Position((position.getRow() + 1), (position.getColumn() + 1))).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    moves.add(new Move(position, new Position((position.getRow() + 1), (position.getColumn() + 1)), promotionPiece));
                }
            }
            if (isOnBoard(new Position((position.getRow() + 1), (position.getColumn() - 1)))
                    && board.getPiece(new Position((position.getRow() + 1), (position.getColumn() - 1))) != null) {
                if (board.getPiece(new Position((position.getRow() + 1), (position.getColumn() - 1))).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    moves.add(new Move(position, new Position((position.getRow() + 1), (position.getColumn() - 1)), promotionPiece));
                }
            }
        } else if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (isOnBoard(new Position((position.getRow() - 1), (position.getColumn() - 1)))
                    && board.getPiece(new Position((position.getRow() - 1), (position.getColumn() - 1))) != null) {
                if (board.getPiece(new Position((position.getRow() - 1), (position.getColumn() - 1))).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    moves.add(new Move(position, new Position((position.getRow() - 1), (position.getColumn() - 1)), promotionPiece));
                }
            }
            if (isOnBoard(new Position((position.getRow() - 1), (position.getColumn() + 1)))
                    && board.getPiece(new Position((position.getRow() - 1), (position.getColumn() + 1))) != null) {
                if (board.getPiece(new Position((position.getRow() - 1), (position.getColumn() + 1))).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    moves.add(new Move(position, new Position((position.getRow() - 1), (position.getColumn() + 1)), promotionPiece));
                }
            }
        }
        return moves;
    }
}
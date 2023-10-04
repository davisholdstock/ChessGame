package main;

import chess.*;

import java.util.ArrayList;

public class Rules extends Game{

    public Rules() {

    }
    public ArrayList<ChessMove> pawnMoves(ChessBoard board, ChessPosition startPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        if (isOnBoard(startPosition)) {
//            checkForPawnCapture(board, startPosition, moves);
            if (PawnOnStartingSquare(startPosition, board.getPiece(startPosition).getTeamColor())) { // Move two squares at fist
                for (int i = 1; i <= 2; ++i) {
                    if (moveForward(i, startPosition, board) != null)
                        moves.add(moveForward(i, startPosition, board));
                    else break;
                }
            } else if (pawnCanPromote(startPosition, board.getPiece(startPosition).getTeamColor())) { // Move one square and Promote
                for (var type: ChessPiece.PieceType.values()) {
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
        if (moveForward(1, startPosition, board) != null)
            moves.add(moveForward(1, startPosition, board));
        if (moveBackward(1, startPosition, board) != null)
            moves.add(moveBackward(1, startPosition, board));
        if (moveLeft(1, startPosition, board) != null)
            moves.add(moveLeft(1, startPosition, board));
        if (moveRight(1, startPosition, board) != null)
            moves.add(moveRight(1, startPosition, board));
        if (moveForwardRight(1, startPosition, board) != null)
            moves.add(moveForwardRight(1, startPosition, board));
        if (moveForwardLeft(1, startPosition, board) != null)
            moves.add(moveForwardLeft(1, startPosition, board));
        if (moveBackwardRight(1, startPosition, board) != null)
            moves.add(moveBackwardRight(1, startPosition, board));
        if (moveBackwardLeft(1, startPosition, board) != null)
            moves.add(moveBackwardLeft(1, startPosition, board));
        return moves;
    }

    private ChessMove moveForward(int numSquares, ChessPosition position, ChessBoard board) {
            if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                if (isOnBoard(new Position((position.getRow() + numSquares), position.getColumn()))
                        && (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) == null
                        || (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())).getTeamColor() == TeamColor.BLACK
                        && board.getPiece(position).getPieceType() != ChessPiece.PieceType.PAWN)))
                    return (new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), null));
                return null;
            }
            else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
                if (isOnBoard(new Position((position.getRow() - numSquares), position.getColumn()))
                        && (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())) == null
                        || (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())).getTeamColor() == TeamColor.WHITE
                        && board.getPiece(position).getPieceType() != ChessPiece.PieceType.PAWN)))
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
        }
        else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
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
                    || board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())).getTeamColor() == TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() - numSquares), position.getColumn()), null));
            return null;
        }
        else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), position.getColumn()))
                    && (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())).getTeamColor() == TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), null));
            return null;
        }
        return null;
    }

    private ChessMove moveRight(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position(position.getRow(), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position(position.getRow(), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position(position.getRow(), (position.getColumn() + numSquares))).getTeamColor() == TeamColor.BLACK)) // Can Capture opposite color
                return (new Move(position, new Position(position.getRow(), (position.getColumn() + numSquares)), null));
            return null;
        }
        else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position(position.getRow(), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position(position.getRow(), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position(position.getRow(), (position.getColumn() - numSquares))).getTeamColor() == TeamColor.WHITE)) // Can Capture opposite color
                return (new Move(position, new Position(position.getRow(), (position.getColumn() - numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveLeft(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position(position.getRow(), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position(position.getRow(), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position(position.getRow(), (position.getColumn() - numSquares))).getTeamColor() == TeamColor.BLACK))
                return (new Move(position, new Position(position.getRow(), (position.getColumn() - numSquares)), null));
            return null;
        }
        else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position(position.getRow(), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position(position.getRow(), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position(position.getRow(), (position.getColumn() + numSquares))).getTeamColor() == TeamColor.WHITE))
                return (new Move(position, new Position(position.getRow(), (position.getColumn() + numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveForwardRight(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares))).getTeamColor() == TeamColor.BLACK))
                return (new Move(position, new Position((position.getRow() + numSquares), (position.getColumn() + numSquares)), null));
            return null;
        }
        else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares))).getTeamColor() == TeamColor.WHITE))
                return (new Move(position, new Position((position.getRow() - numSquares), (position.getColumn() - numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveForwardLeft(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares))).getTeamColor() == TeamColor.BLACK))
                return (new Move(position, new Position((position.getRow() + numSquares), (position.getColumn() - numSquares)), null));
            return null;
        }
        else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares))).getTeamColor() == TeamColor.WHITE))
                return (new Move(position, new Position((position.getRow() - numSquares), (position.getColumn() + numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveBackwardRight(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() + numSquares))).getTeamColor() == TeamColor.BLACK))
                return (new Move(position, new Position((position.getRow() - numSquares), (position.getColumn() + numSquares)), null));
            return null;
        }
        else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() - numSquares))).getTeamColor() == TeamColor.WHITE))
                return (new Move(position, new Position((position.getRow() + numSquares), (position.getColumn() - numSquares)), null));
            return null;
        }
        return null;
    }

    private ChessMove moveBackwardLeft(int numSquares, ChessPosition position, ChessBoard board) {
        if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (isOnBoard(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares)))
                    && (board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares))) == null
                    || board.getPiece(new Position((position.getRow() - numSquares), (position.getColumn() - numSquares))).getTeamColor() == TeamColor.BLACK))
                return (new Move(position, new Position((position.getRow() - numSquares), (position.getColumn() - numSquares)), null));
            return null;
        }
        else if (board.getPiece(position).getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
            if (isOnBoard(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares)))
                    && (board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares))) == null
                    || board.getPiece(new Position((position.getRow() + numSquares), (position.getColumn() + numSquares))).getTeamColor() == TeamColor.WHITE))
                return (new Move(position, new Position((position.getRow() + numSquares), (position.getColumn() + numSquares)), null));
            return null;
        }
        return null;
    }

    private boolean isOnBoard (ChessPosition position) {
        return (position.getRow() >= 0
                && position.getRow() <= (getGame().getBoard().getRows() - 1)
                && position.getColumn() >= 0
                && position.getColumn() <= (getGame().getBoard().getColumns()) - 1);
    }

    public boolean PawnOnStartingSquare(ChessPosition position, TeamColor color) {
        if (color.equals(TeamColor.WHITE))
            return (position.getRow() == 1);
        return (position.getRow() == 6);
    }

    public boolean pawnCanPromote(ChessPosition position, TeamColor color) {
        if (color.equals(TeamColor.WHITE))
            return (position.getRow() == 6);
        return (position.getRow() == 1);
    }

    public ArrayList<ChessMove> checkForPawnCapture(ChessBoard board, ChessPosition position, ArrayList<ChessMove> moves, ChessPiece.PieceType promotionPiece) {
        if (board.getPiece(position).getTeamColor() == TeamColor.WHITE) {
            if (board.getPiece(new Position((position.getRow() + 1), (position.getColumn() + 1))) != null){
                if (board.getPiece(new Position((position.getRow() + 1), (position.getColumn() + 1))).getTeamColor() == TeamColor.BLACK) {
                    moves.add(new Move(position, new Position((position.getRow() + 1), (position.getColumn() + 1)), promotionPiece));
                }
            }
            if (board.getPiece(new Position((position.getRow() + 1), (position.getColumn() - 1))) != null){
                if (board.getPiece(new Position((position.getRow() + 1), (position.getColumn() - 1))).getTeamColor() == TeamColor.BLACK) {
                    moves.add(new Move(position, new Position((position.getRow() + 1), (position.getColumn() - 1)), promotionPiece));
                }
            }
        }
        else if (board.getPiece(position).getTeamColor() == TeamColor.BLACK) {
            if (board.getPiece(new Position((position.getRow() - 1), (position.getColumn() - 1))) != null){
                if (board.getPiece(new Position((position.getRow() - 1), (position.getColumn() - 1))).getTeamColor() == TeamColor.WHITE) {
                    moves.add(new Move(position, new Position((position.getRow() - 1), (position.getColumn() - 1)), promotionPiece));
                }
            }
            if (board.getPiece(new Position((position.getRow() - 1), (position.getColumn() + 1))) != null){
                if (board.getPiece(new Position((position.getRow() - 1), (position.getColumn() + 1))).getTeamColor() == TeamColor.WHITE) {
                    moves.add(new Move(position, new Position((position.getRow() - 1), (position.getColumn() + 1)), promotionPiece));
                }
            }
        }
        return moves;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    public ArrayList<ChessMove> pawnMoves(ChessPosition startPosition) {
//        ArrayList<ChessMove> moves = new ArrayList<>();
//
//        if (isOnBoard(startPosition)) { // Move two squares at fist
//            if (PawnOnStartingSquare(startPosition, board.getPiece(startPosition).getTeamColor())) {
//                for (int i = 1; i <= 2; ++i) {
//                    if (moveForward(i, startPosition) != null)
//                        moves.add(moveForward(i, startPosition));
//                    else break;
//                }
//            }
//            else { // Move One square
//                if (board.getPiece(startPosition).getTeamColor().equals(TeamColor.WHITE))
//                    if (board.getPiece(new Position((startPosition.getRow() + 1), startPosition.getColumn())) != null)
//                        moves.add(new Move(startPosition, new Position((startPosition.getRow() + 1), startPosition.getColumn()), null));
//                    else
//                    if (board.getPiece(new Position((startPosition.getRow() - 1), startPosition.getColumn())) != null)
//                        moves.add(new Move(startPosition, new Position((startPosition.getRow() - 1), startPosition.getColumn()), null));
//            }
//        }
//
//        // FIXME can't en passant, promote, or capture
//
//        return moves;
//    }
//
//    public ArrayList<ChessMove> rookMoves(ChessPosition startPosition) {
//        ArrayList<ChessMove> moves = new ArrayList<>();
//
//        return moves;
//    }
//
//    public ArrayList<ChessMove> bishopMoves(ChessPosition startPosition) {
//        ArrayList<ChessMove> moves = new ArrayList<>();
//
//        return moves;
//    }
//
//    public ArrayList<ChessMove> knightMoves(ChessPosition startPosition) {
//        ArrayList<ChessMove> moves = new ArrayList<>();
//
//        return moves;
//    }
//
//    public ArrayList<ChessMove> kingMoves(ChessPosition startPosition) {
//        ArrayList<ChessMove> moves = new ArrayList<>();

//        if (isOnBoard(start) && isOnBoard(end)) {
//            if (moveRight(1)) return true;
//            if (moveLeft(1)) return true;
//            if (moveForward(1)) return true;
//            if (moveBackward(1)) return true;
//            if (moveForwardRight(1)) return true;
//            if (moveForwardLeft(1)) return true;
//            if (moveBackwardRight(1)) return true;
//            if (moveBackwardLeft(1)) return true;
//            return false;
//        }
//        return false;
//
//        return moves;
//    }
//
//    public ArrayList<ChessMove> queenMoves(ChessPosition startPosition) {
//        ArrayList<ChessMove> moves = new ArrayList<>();
//
//        return moves;
//    }
//
//    private boolean isOnBoard (ChessPosition position) {
//        return (position.getRow() >= 1
//                && position.getRow() <= getGame().getBoard().getRows()
//                && position.getColumn() >= 1
//                && position.getColumn() <= getGame().getBoard().getColumns());
//    }
//
//    public boolean PawnOnStartingSquare(ChessPosition position, TeamColor color) {
//        if (color.equals(TeamColor.WHITE))
//            return (position.getRow() == 1);
//        return (position.getRow() == 7);
//    }
//
//    private ChessMove moveForward(int numSquares, ChessPosition position) {
//        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
//            if (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) != null
//                    && isOnBoard(new Position((position.getRow() + numSquares), position.getColumn()))) {
//                return new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), null);
//            }
//            return null;
//        }
//        if (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())) != null
//                && isOnBoard(new Position((position.getRow() - numSquares), position.getColumn())))
//            return new Move(position, new Position((position.getRow() - numSquares), position.getColumn()), null);
//        return null;
//    }
//
//    private ChessMove moveBackward(int numSquares, ChessPosition position) {
//        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
//            if (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn())) != null
//                    && isOnBoard(new Position((position.getRow() - numSquares), position.getColumn()))) {
//                return new Move(position, new Position((position.getRow() - numSquares), position.getColumn()), null);
//            }
//            return null;
//        }
//        if (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn())) != null
//                && isOnBoard(new Position((position.getRow() + numSquares), position.getColumn())))
//            return new Move(position, new Position((position.getRow() + numSquares), position.getColumn()), null);
//        return null;
//    }
//
//    private ChessMove moveRight(int numSquares, ChessPosition position) {
//        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
//            if (board.getPiece(new Position((position.getRow()), position.getColumn() + numSquares)) != null
//                    && isOnBoard(new Position((position.getRow()), position.getColumn() + numSquares))) {
//                return new Move(position, new Position((position.getRow()), position.getColumn() + numSquares), null);
//            }
//            return null;
//        }
//        if (board.getPiece(new Position((position.getRow()), position.getColumn() - numSquares)) != null
//                && isOnBoard(new Position((position.getRow()), position.getColumn() - numSquares)))
//            return new Move(position, new Position((position.getRow()), position.getColumn() - numSquares), null);
//        return null;
//    }
//
//    private ChessMove moveLeft(int numSquares, ChessPosition position) {
//        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
//            if (board.getPiece(new Position((position.getRow()), position.getColumn() - numSquares)) != null
//                    && isOnBoard(new Position((position.getRow()), position.getColumn() - numSquares))) {
//                return new Move(position, new Position((position.getRow()), position.getColumn() - numSquares), null);
//            }
//            return null;
//        }
//        if (board.getPiece(new Position((position.getRow()), position.getColumn() + numSquares)) != null
//                && isOnBoard(new Position((position.getRow()), position.getColumn() + numSquares)))
//            return new Move(position, new Position((position.getRow()), position.getColumn() + numSquares), null);
//        return null;
//    }
//
//    private ChessMove moveForwardRight(int numSquares, ChessPosition position) {
//        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
//            if (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn() + numSquares)) != null
//                    && isOnBoard(new Position((position.getRow() + numSquares), position.getColumn() + numSquares))) {
//                return new Move(position, new Position((position.getRow() + numSquares), position.getColumn() + numSquares), null);
//            }
//            return null;
//        }
//        if (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn() - numSquares)) != null
//                && isOnBoard(new Position((position.getRow() - numSquares), position.getColumn() - numSquares)))
//            return new Move(position, new Position((position.getRow() - numSquares), position.getColumn() - numSquares), null);
//        return null;
//    }
//
//    private ChessMove moveForwardLeft(int numSquares, ChessPosition position) {
//        if (board.getPiece(position).getTeamColor().equals(TeamColor.WHITE)) {
//            if (board.getPiece(new Position((position.getRow() + numSquares), position.getColumn() - numSquares)) != null
//                    && isOnBoard(new Position((position.getRow() + numSquares), position.getColumn() - numSquares))) {
//                return new Move(position, new Position((position.getRow() + numSquares), position.getColumn() - numSquares), null);
//            }
//            return null;
//        }
//        if (board.getPiece(new Position((position.getRow() - numSquares), position.getColumn() + numSquares)) != null
//                && isOnBoard(new Position((position.getRow() - numSquares), position.getColumn() + numSquares)))
//            return new Move(position, new Position((position.getRow() - numSquares), position.getColumn() + numSquares), null);
//        return null;
//    }
//
//    private boolean moveBackwardRight(int numSquares) {
//        return ((start.getColumn() + numSquares) == end.getColumn() && (start.getRow() - numSquares) == end.getRow());
//    }
//
//    private boolean moveBackwardLeft(int numSquares) {
//        return ((start.getColumn() - numSquares) == end.getColumn() && (start.getRow() - numSquares) == end.getRow());
//    }
}

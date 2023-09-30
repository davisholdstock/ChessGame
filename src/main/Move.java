import chess.*;

public class Move extends Game implements ChessMove {
    ChessPiece.PieceType promotionPiece;
    ChessPiece piece;
    ChessPosition start;
    ChessPosition end;

    public Move(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionPiece) {
        this.promotionPiece = promotionPiece;
        this.start = start;
        this.end = end;
        this.piece = getGame().getBoard().getPiece(start);
    }

    @Override
    public ChessPosition getStartPosition() {
        return start;
    }

    @Override
    public ChessPosition getEndPosition() {
        if (piece != null) {
            switch (piece.getPieceType()) {
                case PAWN -> {
                    if (PawnMove())
                        return end;
                    return start;
                }
                case ROOK -> {
                    if (RookMove())
                        return end;
                    return start;
                }
                case BISHOP -> {
                    if (BishopMove())
                        return end;
                    return start;
                }
                case KNIGHT -> {
                    if (KnightMove())
                        return end;
                    return start;
                }
                case KING -> {
                    if (KingMove())
                        return end;
                    return start;
                }
                case QUEEN -> {
                    if (QueenMove())
                        return end;
                    return start;
                }
                default -> {
                    return start;
                }
            }
        }
        return start;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    private boolean PawnMove() {
        if (piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)
                && ((start.getRow() + 1) == (end.getRow()))
                && start.getRow() >= 1
                && end.getRow() <= 8) {
            return true;
        } else if (piece.getTeamColor().equals(ChessGame.TeamColor.BLACK)
                && ((start.getRow() - 1) == (end.getRow()))
                && start.getRow() <= 8
                && end.getRow() >= 1) {
            return true;
        }
        return false;
    }

    private boolean RookMove() {
        if (start.getRow() == end.getRow()
                && start.getColumn() >= 1
                && start.getColumn() <= 8
                && end.getColumn() >= 1
                && end.getColumn() <= 8) {
            return true;
        }else if (start.getColumn() == end.getColumn()
                && start.getRow() >= 1
                && start.getRow() <= 8
                && end.getRow() >= 1
                && end.getRow() <= 8) {
            return true;
        }
        return false;
    }

    private boolean BishopMove() {return false;}

    private boolean KnightMove() {return false;}

    private boolean QueenMove() {return false;}

    private boolean KingMove() {
        if (isOnBoard(start) && isOnBoard(end)) {
            // Move right
            if (start.getRow() == end.getRow()
                    && (start.getColumn() + 1) == end.getColumn()) {
                return true;
            }

            // Move left
            else if (start.getRow() == end.getRow()
                    && (start.getColumn() - 1) == end.getColumn()) {
                return true;
            }

            // Move forward
            else if (start.getColumn() == end.getColumn()
                    && (start.getRow() + 1) == end.getRow()) {
                return true;
            }

            // Move backward
            else if (start.getColumn() == end.getColumn()
                    && (start.getRow() + 1) == end.getRow()) {
                return true;
            }

            return false;
        }
        return false;
    }

    private boolean isOnBoard (ChessPosition position) {
        if (position.getRow() >= 1
                && position.getRow() <= getGame().getBoard().getRows()
                && position.getColumn() >= 1
                && position.getColumn() <= getGame().getBoard().getColumns())
            return true;
        return false;
    }
}

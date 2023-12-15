package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;

public class ChessGUI extends JFrame implements MouseListener {
    private int windowHeight = 500;
    private int windowWidth = 475;
    ChessGame game = new Game();
    private JPanel[][] chessGameBoard = new JPanel[game.getBoard().getRows()][game.getBoard().getColumns()];
    private JPanel pnlMain = new JPanel(new GridLayout(game.getBoard().getRows(), game.getBoard().getColumns()));
    private String[][] strChessBoard = new String[][]{{"RB", "NB", "BB", "QB", "KB", "BB", "NB", "RB"}, {"PB", "PB", "PB", "PB", "PB", "PB", "PB", "PB"}, {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "}, {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "}, {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "}, {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "}, {"PW", "PW", "PW", "PW", "PW", "PW", "PW", "PW"}, {"RW", "NW", "BW", "QW", "KW", "BW", "NW", "RW"}};
    private ImageIcon rookBlack = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/b_rook.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon rookWhite = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/w_rook.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon bishopBlack = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/b_bishop.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon bishopWhite = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/w_bishop.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon knightBlack = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/b_knight.png").getImage().getScaledInstance(windowWidth / 12, windowHeight / 12, Image.SCALE_SMOOTH));
    private ImageIcon knightWhite = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/w_knight.png").getImage().getScaledInstance(windowWidth / 12, windowHeight / 12, Image.SCALE_SMOOTH));
    private ImageIcon kingBlack = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/b_king.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon kingWhite = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/w_king.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon queenBlack = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/b_queen.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon queenWhite = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/w_queen.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon pawnBlack = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/b_pawn.png").getImage().getScaledInstance(windowWidth / 9, windowHeight / 9, Image.SCALE_SMOOTH));
    private ImageIcon pawnWhite = new ImageIcon(new ImageIcon("C:/Users/davis/IdeaProjects/ChessGamePhase1/src/main/main/images/w_pawn.png").getImage().getScaledInstance(windowWidth / 13, windowHeight / 13, Image.SCALE_SMOOTH));
    private boolean boolMoveSelection = false, bWhite = true, bMyTurn = true;
    private Point pntMoveFrom, pntMoveTo;
    private Container c;

    // the whole constructor is for setting up the UI of the form

    public ChessGUI() {
        game.getBoard().resetBoard();
        c = getContentPane();
        setBounds(100, 100, windowWidth, windowHeight);
        setBackground(new Color(204, 204, 204));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chess Game");
        setResizable(false);
        c.setLayout(null);
        pnlMain.setBounds(0, 0, windowWidth - 15, windowHeight - 40);
        pnlMain.setBackground(new Color(255, 255, 255));
        c.add(pnlMain);
        this.drawChessBoard();
        System.out.println(game.toString());
        this.arrangeChessPieces();
        show();

    }

    /**
     * This method captures the move on the chess board and then makes
     * it happen, logically and physically; also, it sends the move to
     * the other client
     */

    public void mouseClicked(MouseEvent e) {
        if (bMyTurn) {
            Object source = e.getSource();
            JPanel pnlTemp = (JPanel) source;
            int intX = (pnlTemp.getX() / 57);
            int intY = (pnlTemp.getY() / 57);
            this.boolMoveSelection = !this.boolMoveSelection;
            if (this.boolMoveSelection) {
                this.pntMoveFrom = new Point(intX, intY);
                if (this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString().trim().equals("") && game.getBoard().getPiece(new Position(this.pntMoveFrom.y, this.pntMoveFrom.x)) == null)
                    this.boolMoveSelection = !this.boolMoveSelection;
                if ((!this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString().trim().equals("")) &&
                        game.getBoard().getPiece(new Position(this.pntMoveFrom.y, this.pntMoveFrom.x)) != null &&
                        !this.bWhite /*&& this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString().charAt(1) == 'B'*/)
                    this.boolMoveSelection = !this.boolMoveSelection;
                if ((!this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString().trim().equals("")) &&
                        game.getBoard().getPiece(new Position(this.pntMoveFrom.y, this.pntMoveFrom.x)) != null &&
                        !this.bWhite /*&& this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString().charAt(1) == 'W'*/)
                    this.boolMoveSelection = !this.boolMoveSelection;
                if (this.boolMoveSelection)
                    this.makeChessPieceDifferent(true);
            } else {
                this.pntMoveTo = new Point(intX, intY);
                if (!this.pntMoveFrom.equals(this.pntMoveTo)) {
                    if (this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString().trim() != "")
                        if (this.isMoveValid(pntMoveFrom.x, pntMoveFrom.y)) {
                            try {
                                this.moveChessPiece();
                            } catch (RuntimeException exception) {
                                throw new RuntimeException(exception);
                            }
                            this.strChessBoard[this.pntMoveTo.y][this.pntMoveTo.x] = this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString();
                            this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x] = "  ";
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid Move Request.", "Warning", JOptionPane.ERROR_MESSAGE);
                            this.makeChessPieceDifferent(false);
                        }
                } else
                    this.makeChessPieceDifferent(false);
            }
        }
    }

    /**
     * This method checks if attempted move is valid or not
     */
    private boolean isMoveValid(int x, int y) {
        boolean isMoveValid = true;
        Collection<ChessMove> moves = game.validMoves(new Position(y, x));
        for (ChessMove move1 : moves) {
        }
        return isMoveValid;
    }

    /**
     * This method makes the selected chess piece looks like selected
     *
     * @param bSelected
     */
    private void makeChessPieceDifferent(boolean bSelected) {
        for (int z = 0; z < this.chessGameBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponentCount(); z++)
            if (this.chessGameBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponent(z).getClass().toString().contains("JLabel")) {
                JLabel lblTemp = (JLabel) this.chessGameBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponent(z);
                lblTemp.setEnabled(!bSelected);
            }
    }

    /**
     * If class level variables Point-From and Point-To are set,
     * then this method actually moves a piece, if any exists, from
     * one cell to the other
     */
    private void moveChessPiece() {
        for (int z = 0; z < this.chessGameBoard[this.pntMoveTo.y][this.pntMoveTo.x].getComponentCount(); z++)
            if (this.chessGameBoard[this.pntMoveTo.y][this.pntMoveTo.x].getComponent(z).getClass().toString().contains("JLabel")) {
                this.chessGameBoard[this.pntMoveTo.y][this.pntMoveTo.x].remove(z);
                this.chessGameBoard[this.pntMoveTo.y][this.pntMoveTo.x].repaint();
            }
        for (int z = 0; z < this.chessGameBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponentCount(); z++)
            if (this.chessGameBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponent(z).getClass().toString().contains("JLabel")) {
                this.chessGameBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].remove(z);
                this.chessGameBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].repaint();
            }
        try {
            game.makeMove(new Move(new Position(pntMoveFrom.y, pntMoveFrom.x), new Position(pntMoveTo.y, pntMoveTo.x), null));
            System.out.println(game.toString());
        } catch (InvalidMoveException e) {
            throw new RuntimeException(e);
        }
        this.chessGameBoard[this.pntMoveTo.y][this.pntMoveTo.x].add(this.getPieceObject(game.getBoard().getPiece(new Position(this.pntMoveTo.y, this.pntMoveTo.x))), BorderLayout.CENTER);
        this.chessGameBoard[this.pntMoveTo.y][this.pntMoveTo.x].validate();
    }

    /**
     * Given the code of a piece as a string, this method instantiates
     * a label object with the right image inside it
     *
     * @param chessPiece
     * @return
     */
    private JLabel getPieceObject(ChessPiece chessPiece) {
        JLabel lblTemp;
        if (chessPiece == null)
            return new JLabel();
        if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK
                && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK)
            return new JLabel(this.rookBlack);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP
                && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK)
            return new JLabel(this.bishopBlack);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT
                && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK)
            return new JLabel(this.knightBlack);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN
                && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK)
            return new JLabel(this.queenBlack);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.KING
                && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK)
            return new JLabel(this.kingBlack);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.PAWN
                && chessPiece.getTeamColor() == ChessGame.TeamColor.BLACK)
            return new JLabel(this.pawnBlack);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.ROOK
                && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE)
            return new JLabel(this.rookWhite);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.BISHOP
                && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE)
            return new JLabel(this.bishopWhite);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.KNIGHT
                && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE)
            return new JLabel(this.knightWhite);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.QUEEN
                && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE)
            return new JLabel(this.queenWhite);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.KING
                && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE)
            return new JLabel(this.kingWhite);
        else if (chessPiece.getPieceType() == ChessPiece.PieceType.PAWN
                && chessPiece.getTeamColor() == ChessGame.TeamColor.WHITE)
            return new JLabel(this.pawnWhite);
        else
            return new JLabel();
    }

    /**
     * This method reads strChessBoard two-dimensional array of string
     * and places chess pieces at their right positions
     */
    private void arrangeChessPieces() {
        for (int y = 0; y < game.getBoard().getColumns(); ++y)
            for (int x = 0; x < game.getBoard().getRows(); ++x) {
                this.chessGameBoard[y][x].add(this.getPieceObject(game.getBoard().getPiece(new Position((game.getBoard().getColumns() - 1) - y, x))), BorderLayout.CENTER);
                this.chessGameBoard[y][x].validate();
            }
    }

    /**
     * This method draws chess board, i.e. black and white cells on the board
     */
    private void drawChessBoard() {
        for (int y = 0; y < game.getBoard().getColumns(); ++y)
            for (int x = 0; x < game.getBoard().getRows(); ++x) {
                chessGameBoard[y][x] = new JPanel(new BorderLayout());
                chessGameBoard[y][x].addMouseListener(this);
                pnlMain.add(chessGameBoard[y][x]);
                if (y % 2 == 0)
                    if (x % 2 != 0)
                        chessGameBoard[y][x].setBackground(Color.DARK_GRAY);
                    else
                        chessGameBoard[y][x].setBackground(Color.WHITE);
                else if (x % 2 == 0)
                    chessGameBoard[y][x].setBackground(Color.DARK_GRAY);
                else
                    chessGameBoard[y][x].setBackground(Color.WHITE);
            }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }
}
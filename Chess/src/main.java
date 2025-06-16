package src;

import Pieces.*;

import java.util.Scanner;
import java.util.regex.Pattern;

public class main {
    private static ChessPiece[][] board;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String first;
        byte x, y, newX, newY, moveLength;
        boolean game = true, whiteTurn = true;
        Vector[] potentialMoves;
        Vector nextMove;
        Pattern validCheck, validMove, concede;

        validMove = Pattern.compile("^[a-h][1-8]\s[a-h][1-8]$");
        validCheck = Pattern.compile("^[a-h][1-8]$");
        concede = Pattern.compile("^concede$");

        setBoard();
        /*
        board = new ChessPiece[8][8];

        board[4][4] = new Bishop(Color.WHITE);
        board[3][5] = new Pawn(Color.BLACK);
        board[5][5] = new Pawn(Color.WHITE);
        */
        printBoard();

        while (game) {
            first = scan.nextLine().trim();
            //if the message is 'concede' we exit the game loop
            if (concede.matcher(first).matches()) game = false;
            //if the message is a valid move (a grid space followed by whitespace followed by a grid space
            else if (validMove.matcher(first).matches()) {
                //retrieve the starting location
                x = (byte) (first.charAt(0) - 'a');
                y = (byte) (first.charAt(1) - '1');
                //check there is a piece there
                if (board[x][y] == null) System.out.println("Empty space!");
                //check that the piece belongs to the color whose turn it is
                else if ((board[x][y].c == Color.WHITE && !whiteTurn) ||  (board[x][y].c == Color.BLACK && whiteTurn)) System.out.println("Cannot move an opponent's piece");
                else {
                    //retrieve the end location
                    newX = (byte) (first.charAt(3) - 'a');
                    newY = (byte) (first.charAt(4) - '1');
                    //check that the start and end locations are not the same
                    if (x == newX && y == newY) System.out.println("Cannot pass your turn");
                    else {
                        //create a vector representing the move (vector stretches to board edge)
                        nextMove = new Vector(x, y, newX, newY);
                        //save the initial move length
                        moveLength = nextMove.limit;
                        //limit the vector if there is a friendly piece in the way or a potential capture
                        limitMove(nextMove, board[x][y].c);
                        //check that the vector is valid for the type of piece and see if the vector is unobstructed
                        if (board[x][y].checkVector(nextMove) && (moveLength == nextMove.limit || board[x][y] instanceof Knight)) {
                            //move the piece, print the new board, swap the turn, and tell the piece it moved
                            board[newX][newY] = board[x][y];
                            board[x][y] = null;
                            printBoard();
                            whiteTurn = !whiteTurn;
                            board[newX][newY].moved();
                        } else System.out.println("Invalid move");
                    }
                }
            //if the message is a valid 'investigate' (one grid space given)
            } else if (validCheck.matcher(first).matches()) {
                //get the space, check if empty
                x = (byte) (first.charAt(0) - 'a');
                y = (byte) (first.charAt(1) - '1');
                if (board[x][y] == null) System.out.println("Empty space!");
                else {
                    //get all potential moves based on piece type and current location
                    potentialMoves = board[x][y].getMoves(x, y);
                    //limit based on other pieces on the board, and print
                    limitMoves(potentialMoves, board[x][y].c);
                    printMoves(potentialMoves);
                }
            } else System.out.println("Invalid syntax");
        }

        scan.close();

    }

    //creates duplicate board, then replaces all appropriate spaces with # to show the piece can move there
    private static void printMoves(Vector[] moves) {
        ChessPiece[][] hypoBoard = new ChessPiece[8][8];
        byte x, y, spaces;

        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++) {
                hypoBoard[x][y] = board[x][y];
            }
        }

        for (Vector move : moves) {
            x = move.x;
            y = move.y;
            for (spaces = -1; spaces < move.limit; spaces++) {
                hypoBoard[x][y] = new PotentialMove();
                x = moveX(move.dir, x);
                y = moveY(move.dir, y);
            }
        }

        printBoard(hypoBoard);
    }

    //return the resulting x coord after moving in the specified direction
    private static byte moveX(Direction dir, byte start) {
        if (dir == Direction.DOWN_RIGHT || dir == Direction.RIGHT || dir == Direction.UP_RIGHT
            || dir == Direction.KNIGHT_UP_RIGHT || dir == Direction.KNIGHT_DOWN_RIGHT) return ++start;
        if (dir == Direction.DOWN_LEFT || dir == Direction.LEFT || dir == Direction.UP_LEFT
            || dir == Direction.KNIGHT_UP_LEFT || dir == Direction.KNIGHT_DOWN_LEFT) return --start;
        if (dir == Direction.KNIGHT_RIGHT_UP || dir == Direction.KNIGHT_RIGHT_DOWN) return (byte) (start + 2);
        if (dir == Direction.KNIGHT_LEFT_DOWN || dir == Direction.KNIGHT_LEFT_UP) return (byte) (start - 2);
        return start;
    }

    //return the resulting y coord after moving in the specified direction
    private static byte moveY(Direction dir, byte start) {
        if (dir == Direction.UP_LEFT || dir == Direction.UP || dir == Direction.UP_RIGHT
            || dir == Direction.KNIGHT_RIGHT_UP || dir == Direction.KNIGHT_LEFT_UP) return ++start;
        if (dir == Direction.DOWN_LEFT || dir == Direction.DOWN || dir == Direction.DOWN_RIGHT
            || dir == Direction.KNIGHT_RIGHT_DOWN || dir == Direction.KNIGHT_LEFT_DOWN) return --start;
        if (dir == Direction.KNIGHT_UP_LEFT || dir == Direction.KNIGHT_UP_RIGHT) return (byte) (start + 2);
        if (dir == Direction.KNIGHT_DOWN_LEFT || dir == Direction.KNIGHT_DOWN_RIGHT) return (byte) (start - 2);
        else return start;
    }

    //set the board up for a standard game
    private static void setBoard() {
        board = new ChessPiece[8][8];

        for (int x = 0; x < 8; x++) {
            board[x][1] = new Pawn(Color.WHITE);
            board[x][6] = new Pawn(Color.BLACK);
        }

        board[0][0] = new Rook(Color.WHITE);
        board[1][0] = new Knight(Color.WHITE);
        board[2][0] = new Bishop(Color.WHITE);
        board[3][0] = new Queen(Color.WHITE);
        board[4][0] = new King(Color.WHITE);
        board[5][0] = new Bishop(Color.WHITE);
        board[6][0] = new Knight(Color.WHITE);
        board[7][0] = new Rook(Color.WHITE);

        board[0][7] = new Rook(Color.BLACK);
        board[1][7] = new Knight(Color.BLACK);
        board[2][7] = new Bishop(Color.BLACK);
        board[3][7] = new Queen(Color.BLACK);
        board[4][7] = new King(Color.BLACK);
        board[5][7] = new Bishop(Color.BLACK);
        board[6][7] = new Knight(Color.BLACK);
        board[7][7] = new Rook(Color.BLACK);
    }

    //helper for an array of moves that need to be limited
    public static void limitMoves(Vector[] potentialMoves, Color team) {
        for (Vector move : potentialMoves) limitMove(move, team);
    }

    //takes in a move generated from a piece's class, then 'limits' it if there is another piece in the way of the vector
    public static void limitMove(Vector move, Color team) {
        byte vx = move.x, vy = move.y, index;
        for (index = 0; index < move.limit; index++) {
            vx = moveX(move.dir, vx);
            vy = moveY(move.dir, vy);
            if (board[vx][vy] != null) {
                if (team != board[vx][vy].c) move.limit((byte) (index + 1));
                else move.limit(index);
            }
        }
    }

    //print the given board to the console
    private static void printBoard(ChessPiece[][] localBoard) {
        StringBuilder str = new StringBuilder();
        int x, y;

        str.append("    a   b   c   d   e   f   g   h\n");
        for (y = 7; y >= 0; y--) {
            str.append("  - - - - - - - - - - - - - - - - -\n");
            str.append(y + 1);
            for (x = 0; x < 8; x++) {
                str.append(" | ");
                if (localBoard[x][y] == null) str.append(' ');
                //all ChessPieces should have a toChar character representation
                else str.append(localBoard[x][y].toChar());
            }
            str.append(" |\n");
        }
        str.append("  - - - - - - - - - - - - - - - - -");


        System.out.println(str.toString());
    }

    //convenience to print the current game board
    private static void printBoard() {
        printBoard(board);
    }
}

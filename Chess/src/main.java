package src;

import Pieces.*;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class main {
    private static ChessPiece[][] board;
    private Vector previousMove;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String first;
        byte x, y, newX, newY, moveLength;
        boolean game = true, whiteTurn = true;
        Vector[] potentialMoves;
        Vector nextMove;
        Pattern validCheck, validMove, concede, castleLeft, castleRight, promotionOptions;

        validMove = Pattern.compile("^[a-h][1-8]\s[a-h][1-8]$");
        validCheck = Pattern.compile("^[a-h][1-8]$");
        concede = Pattern.compile("^concede$");
        castleLeft = Pattern.compile("^castle left$");
        castleRight = Pattern.compile("^castle right$");
        promotionOptions = Pattern.compile("^[1234]$");

        newX = 8;
        newY = 8;

        setBoard();
        /*
        board = new ChessPiece[8][8];

        board[0][0] = new Rook(Color.WHITE);
        board[7][0] = new Rook(Color.WHITE);
        board[4][0] = new King(Color.WHITE);
        board[0][7] = new Rook(Color.BLACK);
        board[7][7] = new Rook(Color.BLACK);
        board[4][7] = new King(Color.BLACK);

        board[3][0] = new Pawn(Color.BLACK);
        board[5][0] = new Pawn(Color.BLACK);
        board[3][7] = new Pawn(Color.BLACK);
        board[5][7] = new Pawn(Color.BLACK);
        */
        printBoard();

        while (game) {
            first = scan.nextLine().trim();
            //if the message is 'concede' we exit the game loop
            if (concede.matcher(first).matches()) game = false;
            //if the message is a valid move (a grid space followed by whitespace followed by a grid space)
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
                        nextMove = limitMove(nextMove, board[x][y].c);
                        //check that the vector is valid for the type of piece and see if the vector is unobstructed
                        if (board[x][y].checkVector(nextMove) && moveLength == nextMove.limit) {
                            //if we get here, we are doing a standard move

                            //TODO: add update for previous vector
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
                    printMoves(potentialMoves, x, y);
                }
            //if the user wants to castle right
            } else if (castleRight.matcher(first).matches()) {
                try {
                    if (whiteTurn) {
                        if (board[5][0] != null || board[6][0] != null)
                            System.out.println("Invalid because other piece(s) obstruct right castle");
                        else if (!(board[7][0] instanceof Rook) || board[7][0].hasMoved())
                            System.out.println("Invalid because the rook has moved");
                        else if (!(board[4][0] instanceof King) || board[4][0].hasMoved())
                            System.out.println("Invalid because the king has moved");
                        else {
                            //successful right castle for white
                            board[6][0] = board[4][0];
                            board[4][0] = null;
                            board[6][0].moved();
                            board[5][0] = board[7][0];
                            board[7][0] = null;
                            board[5][0].moved();
                            whiteTurn = !whiteTurn;
                            printBoard();
                            //'reset' previous move trackers
                            //TODO: add update for previous vector
                        }
                    } else {
                        if (board[5][7] != null || board[6][7] != null)
                            System.out.println("Invalid because other piece(s) obstruct right castle");
                        else if (!(board[7][7] instanceof Rook) || board[7][7].hasMoved())
                            System.out.println("Invalid because the rook has moved");
                        else if (!(board[4][7] instanceof King) || board[4][7].hasMoved())
                            System.out.println("Invalid because the king has moved");
                        else {
                            //successful right castle for black
                            board[6][7] = board[4][7];
                            board[4][7] = null;
                            board[6][7].moved();
                            board[5][7] = board[7][7];
                            board[7][7] = null;
                            board[5][7].moved();
                            whiteTurn = !whiteTurn;
                            printBoard();
                            //'reset' previous move trackers
                            //TODO: add update for previous vector
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: the following was attempted when castling right:");
                    System.out.println(e);
                }
            //if the user wants to castle left
            } else if (castleLeft.matcher(first).matches()) {
                try {
                    if (whiteTurn) {
                        if (board[1][0] != null || board[2][0] != null || board[3][0] != null)
                            System.out.println("Invalid because other piece(s) obstruct left castle");
                        else if (!(board[0][0] instanceof Rook) || board[0][0].hasMoved())
                            System.out.println("Invalid because the rook has moved");
                        else if (!(board[4][0] instanceof King) || board[4][0].hasMoved())
                            System.out.println("Invalid because the king has moved");
                        else {
                            //successful left castle for white
                            board[2][0] = board[4][0];
                            board[4][0] = null;
                            board[2][0].moved();
                            board[3][0] = board[0][0];
                            board[0][0] = null;
                            board[3][0].moved();
                            whiteTurn = !whiteTurn;
                            printBoard();
                            //'reset' previous move trackers
                            //TODO: add update for previous vector
                        }
                    } else {
                        if (board[1][7] != null || board[2][7] != null || board[3][7] != null)
                            System.out.println("Invalid because other piece(s) obstruct left castle");
                        else if (!(board[0][7] instanceof Rook) || board[0][7].hasMoved())
                            System.out.println("Invalid because the rook has moved");
                        else if (!(board[4][7] instanceof King) || board[4][7].hasMoved())
                            System.out.println("Invalid because the king has moved");
                        else {
                            //successful left castle for black
                            board[2][7] = board[4][7];
                            board[4][7] = null;
                            board[2][7].moved();
                            board[3][7] = board[0][7];
                            board[0][7] = null;
                            board[3][7].moved();
                            whiteTurn = !whiteTurn;
                            printBoard();
                            //'reset' previous move trackers
                            //TODO: add update for previous vector
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: the following was attempted when castling left:");
                    System.out.println(e);
                }
            } else System.out.println("Invalid syntax");

            //check for pawn promotion
            if (newX < 8 && newY < 8 && board[newX][newY] instanceof Pawn
                    && ((newY == 7 && board[newX][newY].c == Color.WHITE) || (newY == 0 && board[newX][newY].c == Color.BLACK))) {
                boolean validSelection = false;
                ChessPiece temp;
                Color team = board[newX][newY].c;
                while (!validSelection) {
                    //print options for promotion and wait for input
                    System.out.println("Pawn promotion! Choose option:\n1 Queen\n2 Knight\n3 Rook\n4 Bishop");
                    first = scan.nextLine().trim();
                    if (promotionOptions.matcher(first).matches()) validSelection = true;
                }
                switch (Byte.valueOf(first)) {
                    case 1:
                        temp = new Queen(team);
                        break;
                    case 2:
                        temp = new Knight(team);
                        break;
                    case 3:
                        temp = new Rook(team);
                        break;
                    default:
                        temp = new Bishop(team);
                        break;
                }
                temp.moved();
                board[newX][newY] = temp;
                printBoard();
            }
        }

        scan.close();

    }

    //creates duplicate board, then replaces all appropriate spaces with # to show the piece can move there
    private static void printMoves(Vector[] moves, int pieceX, int pieceY) {
        ChessPiece[][] hypoBoard = new ChessPiece[8][8];
        byte x, y, spaces;

        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++) {
                hypoBoard[x][y] = board[x][y];
            }
        }

        for (Vector move : moves) {
            if (move.dir != Direction.INVALID) {
                x = move.x;
                y = move.y;
                for (spaces = -1; spaces < move.limit; spaces++) {
                    //we assume the piece is an enemy piece here, allied pieces would have been removed from
                    //potential moves by the limiting of the moves earlier
                    if (board[x][y] != null) hypoBoard[x][y] = new PotentialCapture();
                    else hypoBoard[x][y] = new PotentialMove();
                    x = moveX(move.dir, x);
                    y = moveY(move.dir, y);
                }
            }
        }

        hypoBoard[pieceX][pieceY] = new MoveOrigin();

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
        for (byte i = 0; i < potentialMoves.length; i++) potentialMoves[i] = limitMove(potentialMoves[i], team);
    }

    //takes in a move generated from a piece's class, then 'limits' it if there is another piece in the way of the vector
    public static Vector limitMove(Vector move, Color team) {
        //This check ensures that all knight moves are not landing on a piece of the same color
        if (move.dir.compareTo(Direction.UP_LEFT) > 0) {
             if (board[move.x][move.y] != null && board[move.x][move.y].c == team) {
                 return new Vector(Direction.INVALID, move.x, move.y, move.limit);
             }
        } else {
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
        //checks for pawn movement and capture
        if (board[move.x][move.y] instanceof Pawn) {
            if (move.dir == Direction.UP) {
                if (board[move.x][move.y + 1] != null) move = new Vector(Direction.INVALID, move.x, move.y, move.limit);
            } else if (move.dir == Direction.DOWN) {
                if (board[move.x][move.y - 1] != null) move = new Vector(Direction.INVALID, move.x, move.y, move.limit);
            } else if (move.dir == Direction.UP_LEFT) {
                if (!(board[move.x - 1][move.y + 1] != null && board[move.x - 1][move.y + 1].c != team)) {
                    move = new Vector(Direction.INVALID, move.x, move.y, move.limit);
                }
            } else if (move.dir == Direction.UP_RIGHT) {
                if (!(board[move.x + 1][move.y + 1] != null && board[move.x + 1][move.y + 1].c != team)) {
                    move = new Vector(Direction.INVALID, move.x, move.y, move.limit);
                }
            } else if (move.dir == Direction.DOWN_LEFT) {
                if (!(board[move.x - 1][move.y - 1] != null && board[move.x - 1][move.y - 1].c != team)) {
                    move = new Vector(Direction.INVALID, move.x, move.y, move.limit);
                }
            } else if (move.dir == Direction.DOWN_RIGHT) {
                if (!(board[move.x + 1][move.y - 1] != null && board[move.x + 1][move.y - 1].c != team)) {
                    move = new Vector(Direction.INVALID, move.x, move.y, move.limit);
                }
            }
        }
        return move;

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

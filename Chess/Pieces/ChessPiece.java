package Pieces;

import src.*;

//parent class for all types of chess pieces, each piece is in charge of tracking how it moves
public abstract class ChessPiece {
    public final Color c;

    public ChessPiece(Color c) {
        this.c = c;
    }

    //getMoves needs to return all potential moves based ONLY ON the type of the piece and its board location
    public Vector[] getMoves(byte x, byte y) {
        return new Vector[0];
    }
    //Black moves 'down' and white moves 'up'

    //all objects extending the ChessPiece class should have a character representation
    //for use in printing/viewing the board state; white is uppercase and black is lowercase
    public char toChar() {
        return 'E';
    }

    public void moved() {}

    public boolean hasMoved() throws Exception {
        throw new Exception("Cannot check moved on a "+this.getClass());
    }

    //all pieces extending the ChessPiece class should override with their own method that
    //checks if this vector is a valid moved based on piece type
    public boolean checkVector(Vector move) {
        return false;
    }
}

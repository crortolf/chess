package Pieces;

import src.Color;

//this class is used to represent a potential capture of the piece at these coords
//by the piece whose next potential moves are being displayed
public class PotentialCapture extends ChessPiece {

    public PotentialCapture() {
        super(Color.NEITHER);
    }

    public char toChar() {
        return 'X';
    }
}

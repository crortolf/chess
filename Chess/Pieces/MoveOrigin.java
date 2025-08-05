package Pieces;

import src.Color;

//this class represents the starting location of a piece whose next potential moves are being displayed
public class MoveOrigin extends ChessPiece {

    public MoveOrigin() {
        super(Color.NEITHER);
    }

    public char toChar() {
        return '@';
    }
}

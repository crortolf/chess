package Pieces;

import src.Color;

//this class represents a placeholder for all the positions a piece can move to from its current location
//used for printing out all potential moves from current location of a selected piece
public class PotentialMove extends ChessPiece {

    public PotentialMove() {
        super(Color.NEITHER);
    }

    public char toChar() {
        return '#';
    }
}

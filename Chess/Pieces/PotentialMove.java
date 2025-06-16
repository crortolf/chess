package Pieces;

import src.Color;

public class PotentialMove extends ChessPiece {

    public PotentialMove() {
        super(Color.WHITE);
    }

    public char toChar() {
        return '#';
    }
}

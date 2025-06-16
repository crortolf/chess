package Pieces;

import src.*;

public class Pawn extends ChessPiece {

    public Pawn(Color c) {
        super(c);
    }

    public char toChar() {
        if (c == Color.BLACK) return 'p';
        return 'P';
    }

    public boolean checkVector(Vector move) {

        if (move.limit != 1 && !(move.limit == 2 && !moved)) return false;
        return ((c == Color.BLACK && move.dir == Direction.DOWN) || (c == Color.WHITE && move.dir == Direction.UP));
    }

    //pawn regular movement is handled here, pawn capturing must be handled by the board
    public Vector[] getMoves(byte x, byte y) {
        Vector[] moves = new Vector[1];
        if (c == Color.BLACK) {
            if (y == 6) moves[0] = new Vector(Direction.DOWN, x, y, (byte) 2);
            else moves[0] = new Vector(Direction.DOWN, x, y, (byte) 1);
        } else {
            if (y == 1) moves[0] = new Vector(Direction.UP, x, y, (byte) 2);
            else moves[0] = new Vector(Direction.UP, x, y, (byte) 1);
        }
        return moves;
    }

    //most pieces need to be wary of going off the board, this is not an issue as the board handles promotion
}

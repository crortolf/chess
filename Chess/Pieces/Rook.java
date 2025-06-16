package Pieces;

import src.*;
import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(Color c) {
        super(c);
    }

    public char toChar() {
        if (c == Color.BLACK) return 'r';
        return 'R';
    }

    public boolean checkVector(Vector move) {
        return (move.dir == Direction.UP || move.dir == Direction.DOWN
            || move.dir == Direction.RIGHT || move.dir == Direction.LEFT);
    }

    public Vector[] getMoves(byte x, byte y) {
        List<Vector> moves = new ArrayList<>();
        if (x > 0) moves.add(new Vector(Direction.LEFT, x, y, x));
        if (x < 7) moves.add(new Vector(Direction.RIGHT, x, y, (byte) (7 - x)));
        if (y > 0) moves.add(new Vector(Direction.DOWN, x, y, y));
        if (y < 7) moves.add(new Vector(Direction.UP, x, y, (byte) (7 - y)));
        return moves.toArray(new Vector[0]);
    }
}

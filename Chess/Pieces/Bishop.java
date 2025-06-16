package Pieces;

import src.Color;
import src.Direction;
import src.Vector;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece{

    public Bishop(Color c) {
        super(c);
    }

    public char toChar() {
        if (c == Color.BLACK) return 'b';
        return 'B';
    }

    public boolean checkVector(Vector move) {
        return (move.dir == Direction.UP_RIGHT || move.dir == Direction.UP_LEFT
            || move.dir == Direction.DOWN_LEFT || move.dir == Direction.DOWN_RIGHT);
    }

    public Vector[] getMoves(byte x, byte y) {
        List<Vector> moves = new ArrayList<>();
        if (x > 0 && y > 0) moves.add(new Vector(Direction.DOWN_LEFT, x, y, (byte) Math.min(x, y)));
        if (x > 0 && y < 7) moves.add(new Vector(Direction.UP_LEFT, x, y, (byte) Math.min(x, 7 - y)));
        if (x < 7 && y > 0) moves.add(new Vector(Direction.DOWN_RIGHT, x, y, (byte) Math.min(7 - x, y)));
        if (x < 7 && y < 7) moves.add(new Vector(Direction.UP_RIGHT, x, y, (byte) Math.min(7 - x, 7 - y)));
        return moves.toArray(new Vector[0]);
    }
}

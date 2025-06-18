package Pieces;

import src.Color;
import src.Direction;
import src.Vector;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {

    public Queen(Color c) {
        super(c);
    }

    public char toChar() {
        if (c == Color.BLACK) return 'q';
        return 'Q';
    }

    public boolean checkVector(Vector move) {
        return (move.dir.compareTo(Direction.INVALID) > 0 && move.dir.compareTo(Direction.KNIGHT_UP_LEFT) < 0);
    }

    public Vector[] getMoves(byte x, byte y) {
        List<Vector> moves = new ArrayList<>();
        if (x > 0 && y > 0) moves.add(new Vector(Direction.DOWN_LEFT, x, y, (byte) Math.min(x, y)));
        if (x > 0 && y < 7) moves.add(new Vector(Direction.UP_LEFT, x, y, (byte) Math.min(x, 7 - y)));
        if (x < 7 && y > 0) moves.add(new Vector(Direction.DOWN_RIGHT, x, y, (byte) Math.min(7 - x, y)));
        if (x < 7 && y < 7) moves.add(new Vector(Direction.UP_RIGHT, x, y, (byte) Math.min(7 - x, 7 - y)));
        if (x > 0) moves.add(new Vector(Direction.LEFT, x, y, x));
        if (x < 7) moves.add(new Vector(Direction.RIGHT, x, y, (byte) (7 - x)));
        if (y > 0) moves.add(new Vector(Direction.DOWN, x, y, y));
        if (y < 7) moves.add(new Vector(Direction.UP, x, y, (byte) (7 - y)));
        return moves.toArray(new Vector[0]);
    }
}

package Pieces;

import src.Color;
import src.Direction;
import src.Vector;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {
    private boolean moved;

    public King(Color c) {
        super(c);
        moved = false;
    }

    public void moved() {
        moved = true;
    }

    public boolean hasMoved() {
        return moved;
    }

    public char toChar() {
        if (c == Color.BLACK) return 'k';
        return 'K';
    }

    public boolean checkVector(Vector move) {
        return move.limit == 1;
    }

    public Vector[] getMoves(byte x, byte y) {
        List<Vector> moves = new ArrayList<>();
        if (x > 0 && y > 0) moves.add(new Vector(Direction.DOWN_LEFT, x, y, (byte) 1));
        if (x > 0 && y < 7) moves.add(new Vector(Direction.UP_LEFT, x, y, (byte) 1));
        if (x < 7 && y > 0) moves.add(new Vector(Direction.DOWN_RIGHT, x, y, (byte) 1));
        if (x < 7 && y < 7) moves.add(new Vector(Direction.UP_RIGHT, x, y, (byte) 1));
        if (x > 0) moves.add(new Vector(Direction.LEFT, x, y, (byte) 1));
        if (x < 7) moves.add(new Vector(Direction.RIGHT, x, y, (byte) 1));
        if (y > 0) moves.add(new Vector(Direction.DOWN, x, y, (byte) 1));
        if (y < 7) moves.add(new Vector(Direction.UP, x, y, (byte) 1));
        return moves.toArray(new Vector[0]);
    }
}

package Pieces;

import src.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    public Knight(Color c) {
        super(c);
    }

    public char toChar() {
        if (c == Color.BLACK) return 'n';
        return 'N';
    }

    public boolean checkVector(Vector move) {
        return (move.dir == Direction.KNIGHT_DOWN_LEFT || move.dir == Direction.KNIGHT_DOWN_RIGHT
            || move.dir == Direction.KNIGHT_UP_LEFT || move.dir == Direction.KNIGHT_UP_RIGHT
            || move.dir == Direction.KNIGHT_RIGHT_UP || move.dir == Direction.KNIGHT_RIGHT_DOWN
            || move.dir == Direction.KNIGHT_LEFT_UP || move.dir == Direction.KNIGHT_LEFT_DOWN);
    }

    public Vector[] getMoves(byte x, byte y) {
        List<Vector> moves = new ArrayList<>();
        if (x > 0 && y > 1) moves.add(new Vector(Direction.KNIGHT_DOWN_LEFT, (byte) (x - 1), (byte) (y - 2), (byte) 0));
        if (x > 0 && y < 6) moves.add(new Vector(Direction.KNIGHT_UP_LEFT, (byte) (x - 1), (byte) (y + 2), (byte) 0));
        if (x < 7 && y > 1) moves.add(new Vector(Direction.KNIGHT_DOWN_RIGHT, (byte) (x + 1), (byte) (y - 2), (byte) 0));
        if (x < 7 && y < 6) moves.add(new Vector(Direction.KNIGHT_UP_RIGHT, (byte) (x + 1), (byte) (y + 2), (byte) 0));
        if (x > 1 && y > 0) moves.add(new Vector(Direction.KNIGHT_LEFT_DOWN, (byte) (x - 2), (byte) (y - 1), (byte) 0));
        if (x > 1 && y < 7) moves.add(new Vector(Direction.KNIGHT_LEFT_UP, (byte) (x - 2), (byte) (y + 1), (byte) 0));
        if (x < 6 && y > 0) moves.add(new Vector(Direction.KNIGHT_RIGHT_DOWN, (byte) (x + 2), (byte) (y - 1), (byte) 0));
        if (x < 6 && y < 7) moves.add(new Vector(Direction.KNIGHT_RIGHT_UP, (byte) (x + 2), (byte) (y + 1), (byte) 0));
        return moves.toArray(new Vector[0]);
    }
}

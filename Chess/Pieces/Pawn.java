package Pieces;

import src.*;

public class Pawn extends ChessPiece {
    private boolean moved;

    public Pawn(Color c) {
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
        if (c == Color.BLACK) return 'p';
        return 'P';
    }

    //need to assume that the pawn can move forward, or capture on diags
    //limit needs to handle existence of pieces there or not
    public boolean checkVector(Vector move) {
        if (move.limit > 2) return false;
        return ((c == Color.BLACK && blackDirCheck(move.dir, move.limit))
                || (c == Color.WHITE && whiteDirCheck(move.dir, move.limit)));
    }

    private boolean blackDirCheck(Direction dir, int limit) {
        if (limit == 2) {
            if (!moved) return dir == Direction.DOWN;
            else return false;
        }
        return dir == Direction.DOWN_LEFT || dir == Direction.DOWN || dir == Direction.DOWN_RIGHT;
    }

    private boolean whiteDirCheck(Direction dir, int limit) {
        if (limit == 2) {
            if (!moved) return dir == Direction.UP;
            return false;
        }
        return dir == Direction.UP_LEFT || dir == Direction.UP || dir == Direction.UP_RIGHT;
    }

    //for pawn movement we assume there is nothing in front and also
    //that there is a piece on either side. limiting must be done by the board
    public Vector[] getMoves(byte x, byte y) {
        Vector[] moves = new Vector[3];
        if (c == Color.BLACK) {
            if (y == 6  && !moved) moves[0] = new Vector(Direction.DOWN, x, y, (byte) 2);
            else moves[0] = new Vector(Direction.DOWN, x, y, (byte) 1);

            if (x == 0) moves[1] = new Vector(Direction.INVALID, x, y, (byte) 0);
            else moves[1] = new Vector(Direction.DOWN_LEFT, x, y, (byte) 1);

            if (x == 7) moves[2] = new Vector(Direction.INVALID, x, y, (byte) 0);
            else moves[2] = new Vector(Direction.DOWN_RIGHT, x, y, (byte) 1);
        } else {
            if (y == 1 && !moved) moves[0] = new Vector(Direction.UP, x, y, (byte) 2);
            else moves[0] = new Vector(Direction.UP, x, y, (byte) 1);

            if (x == 0) moves[1] = new Vector(Direction.INVALID, x, y, (byte) 0);
            else moves[1] = new Vector(Direction.UP_LEFT, x, y, (byte) 1);

            if (x == 7) moves[2] = new Vector(Direction.INVALID, x, y, (byte) 0);
            else moves[2] = new Vector(Direction.UP_RIGHT, x, y, (byte) 1);
        }
        return moves;
    }

    //most pieces need to be wary of going off the board, this is not an issue as the board handles promotion
}

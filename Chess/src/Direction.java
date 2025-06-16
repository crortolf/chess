package src;

public enum Direction {
    INVALID,
    UP,
    UP_RIGHT,
    RIGHT,
    DOWN_RIGHT,
    DOWN,
    DOWN_LEFT,
    LEFT,
    UP_LEFT,
    KNIGHT_UP_LEFT,
    KNIGHT_UP_RIGHT,
    KNIGHT_RIGHT_UP,
    KNIGHT_RIGHT_DOWN,
    KNIGHT_DOWN_RIGHT,
    KNIGHT_DOWN_LEFT,
    KNIGHT_LEFT_DOWN,
    KNIGHT_LEFT_UP
}
//naming convention: anything starting with knight is order sensitive, first direction is greater
//ex: left up is 2 left and 1 up, while up left is 2 up and 1 left
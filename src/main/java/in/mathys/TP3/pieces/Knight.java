package in.mathys.TP3.pieces;

import in.mathys.TP3.libs.Position;

public class Knight extends Piece {

    public Knight(Position position, int color) {
        super(position, color);
        setIcon('♞');
        setScore(3);
    }
}

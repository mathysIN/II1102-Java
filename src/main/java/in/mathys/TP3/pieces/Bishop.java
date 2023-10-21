package in.mathys.TP3.pieces;

import in.mathys.TP3.libs.Position;

public class Bishop extends Piece {

    public Bishop(Position position, int color) {
        super(position, color);
        setIcon('♝');
        setScore(3);
    }
}

package in.mathys.TP3.pieces;

import in.mathys.TP3.libs.Position;

public class Queen extends Piece {
    public Queen(Position position, int color) {
        super(position, color);
        setIcon('♛');
        setScore(10);
    }
}

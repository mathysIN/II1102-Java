package in.mathys.TP3.pieces;

import in.mathys.TP3.libs.Position;

public class Rook extends Piece {
    public Rook(Position position, int color) {
        super(position, color);
        setIcon('♜');
        setScore(5);
    }
}

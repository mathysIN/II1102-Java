package in.mathys.tp3.pieces;

import in.mathys.tp3.libs.Position;
import in.mathys.tp3.libs.text.Icons;

public class Bishop extends Piece {

    public Bishop(Position position, int color) {
        super(position, color);
        setIcon(Icons.BISHOP);
        setScore(3);
    }
}

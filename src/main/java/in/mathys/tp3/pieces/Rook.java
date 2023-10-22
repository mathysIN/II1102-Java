package in.mathys.tp3.pieces;

import in.mathys.tp3.libs.Position;
import in.mathys.tp3.libs.text.Icons;

public class Rook extends Piece {
    public Rook(Position position, int color) {
        super(position, color);
        setIcon(Icons.ROOK);
        setScore(5);
    }
}

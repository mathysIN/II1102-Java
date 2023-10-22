package in.mathys.tp3.pieces;

import in.mathys.tp3.libs.Position;
import in.mathys.tp3.libs.text.Icons;

public class Queen extends Piece {
    public Queen(Position position, int color) {
        super(position, color);
        setIcon(Icons.QUEEN);
        setScore(10);
    }
}

package in.mathys.tp3.pieces;

import in.mathys.tp3.libs.Position;
import in.mathys.tp3.libs.text.Icons;

public class Knight extends Piece {

    public Knight(Position position, int color) {
        super(position, color);
        setIcon(Icons.KNIGHT);
        setScore(3);
    }
}

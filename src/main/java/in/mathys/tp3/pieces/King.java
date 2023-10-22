package in.mathys.tp3.pieces;

import in.mathys.tp3.libs.Position;
import in.mathys.tp3.libs.text.Icons;

public class King extends Piece {

    public King(Position position, int color) {
        super(position, color);
        setIcon(Icons.KING);
    }
}

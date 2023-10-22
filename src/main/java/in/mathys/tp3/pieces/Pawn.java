package in.mathys.tp3.pieces;

import in.mathys.tp3.libs.Position;
import in.mathys.tp3.libs.text.Icons;

public class Pawn extends Piece {
    private boolean enPassant = false;

    public Pawn(Position position, int color) {
        super(position, color);
        setIcon(Icons.PAWN);
        setScore(1);
    }

    @Override
    public void setPosition(Position position, boolean superficial) {
        if(!superficial) {
            int distance = Math.abs(this.getPosition().getRow() - position.getRow());
            if(!hasMovedOnce() && distance == 2) enPassant = true;
            else enPassant = false;
        }
        super.setPosition(position, superficial);
    }

    public boolean canBeEnPassant() {
        return enPassant;
    }
}

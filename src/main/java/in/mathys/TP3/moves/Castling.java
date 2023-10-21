package in.mathys.TP3.moves;

import in.mathys.TP3.libs.Cell;
import in.mathys.TP3.libs.Position;

public class Castling extends Move {
    Cell targetedRook;
    Cell newRookPosition;
    public Castling(Position position, Cell targetedRook, Cell newRookPosition) {
        super(position);
        this.targetedRook = targetedRook;
        this.newRookPosition = newRookPosition;
    }

    public Cell getTargetedRook() {
        return targetedRook;
    }

    public Cell getNewRookCell() {
        return newRookPosition;
    }
}

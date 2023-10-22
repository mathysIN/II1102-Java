package in.mathys.tp3.moves;

import in.mathys.tp3.libs.Cell;
import in.mathys.tp3.libs.Position;

public class EnPassant extends Move {
    Cell attackedPawn;
    public EnPassant(Position position, Cell attackedPawn) {
        super(position);
        this.attackedPawn = attackedPawn;
    }

    public Cell getAttackedPawn() {
        return attackedPawn;
    }
}

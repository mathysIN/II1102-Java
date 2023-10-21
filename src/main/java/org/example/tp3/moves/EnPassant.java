package org.example.tp3.moves;

import org.example.tp3.libs.Cell;
import org.example.tp3.libs.Position;
import org.example.tp3.pieces.Pawn;

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

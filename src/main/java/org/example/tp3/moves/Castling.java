package org.example.tp3.moves;

import org.example.tp3.libs.Cell;
import org.example.tp3.libs.Position;

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

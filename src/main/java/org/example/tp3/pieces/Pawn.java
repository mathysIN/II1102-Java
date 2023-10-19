package org.example.tp3.pieces;

import org.example.tp3.libs.Position;

public class Pawn extends Piece {
    public Pawn(Position position, int color) {
        super(position, color);
        setIcon('♟');
        setScore(1);
    }
}

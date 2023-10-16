package org.example.tp3.pieces;

import org.example.tp3.libs.Position;

public class Rook extends Piece {
    public Rook(Position position, int color) {
        super(position, color);
        setIcon('♜');
        setScore(5);
    }
}

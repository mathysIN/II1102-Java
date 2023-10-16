package org.example.tp3.pieces;

import org.example.tp3.libs.Position;

public class Pion extends Piece {
    public Pion(Position position, int color) {
        super(position, color);
        setIcon('♟');
        setScore(1);
    }
}

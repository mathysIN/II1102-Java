package org.example.tp3.pieces;

import org.example.tp3.libs.Position;

public class Bishop extends Piece {
    public Bishop(Position position, int color) {
        super(position, color);
        setIcon('♝');
        setScore(3);
    }
}

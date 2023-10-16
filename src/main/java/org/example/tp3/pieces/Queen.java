package org.example.tp3.pieces;

import org.example.tp3.libs.Position;

public class Queen extends Piece {
    public Queen(Position position, int color) {
        super(position, color);
        setIcon('♛');
        setScore(10);
    }
}

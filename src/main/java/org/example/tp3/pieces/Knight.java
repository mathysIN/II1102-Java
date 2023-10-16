package org.example.tp3.pieces;

import org.example.tp3.libs.Position;

public class Knight extends Piece {
    public Knight(Position position, int color) {
        super(position, color);
        setIcon('♞');
        setScore(3);
    }
}

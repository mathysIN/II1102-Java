package org.example.tp3.pieces;

import org.example.tp3.libs.Position;

public class DummyKing extends King {
    public DummyKing(Position position, int color) {
        super(position, color);
    }

    public DummyKing(King king) {
        super(king.getPosition(), king.getColor());
    }
}

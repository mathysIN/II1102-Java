package org.example.tp3.pieces;

import org.example.tp3.libs.Position;

public class DummyKing extends King {
    King king;
    public DummyKing(King king) {
        super(king.getPosition(), king.getColor());
        this.king = king;
    }

    public King getKing() {
        return king;
    }
}

package org.example.tp3.pieces;

import org.example.tp3.Chess;
import org.example.tp3.libs.Position;

public abstract class Piece {
    private Position position;
    private int color;
    private char icon = '?';
    private boolean hasMovedOnce = false;
    private int score = 0;

    public Piece(Position position, int color) {
        this.position = position;
        this.color = color;
    }

    public int forward(int move) {
        return getColor() == 0 ? -move : move;
    }

    @Override
    public String toString() {
        return getRenderColor() + icon;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        if(!hasMovedOnce) hasMovedOnce = true;
        this.position = position;
    }

    public int getColor() {
        return color;
    }

    public String getRenderColor() {
        return Chess.getPlayerRenderColor(color);
    }

    public boolean isHasMovedOnce() {
        return hasMovedOnce;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public char getIcon() {
        return icon;
    }

    public void setIcon(char icon) {
        this.icon = icon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

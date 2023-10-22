package in.mathys.tp3.pieces;

import in.mathys.tp3.Chess;
import in.mathys.tp3.libs.Position;
import in.mathys.tp3.libs.text.Icons;

public abstract class Piece {
    private Position position;
    private int color;
    protected char icon = Icons.UNKNOWN;
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

    public String getDisplayName() {
        return getIcon() + " " + this.getName();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        setPosition(position, false);
    }

    public void setPosition(Position position, boolean superficial) {
        this.position = position;
    }

    public int getColor() {
        return color;
    }

    public String getRenderColor() {
        return Chess.getPlayerRenderColor(color);
    }

    public boolean hasMovedOnce() {
        return hasMovedOnce;
    }

    public void setHasMovedOnce(boolean hasMovedOnce) {
        this.hasMovedOnce = hasMovedOnce;
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

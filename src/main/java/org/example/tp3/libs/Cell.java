package org.example.tp3.libs;

import org.example.tp3.pieces.Piece;

public class Cell {
    private Position position;
    private Piece piece;
    public Cell(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if(piece != null) piece.setPosition(this.getPosition(), true);
    }

    public boolean isEmpty() {
        return piece == null;
//        return isEmpty;
    }
    @Override
    public String toString() {
        return "Cell{" +
                "position=" + position +
                ", piece=" + piece +
                '}';
    }
}

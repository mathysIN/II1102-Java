package org.example.tp3.libs;

import org.example.tp3.Chess;
import org.example.tp3.libs.Color;
import org.example.tp3.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int color;
    private List<Piece> eatenPieces = new ArrayList<>();

    public Player(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return getRenderColor() + name + Color.RESET;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getRenderColor() {
        return Chess.getPlayerRenderColor(color);
    }

    public List<Piece> getEatenPieces() {
        return eatenPieces;
    }

    public void addEatenPiece(Piece piece) {
        eatenPieces.add(piece);
    }
}

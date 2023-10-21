package org.example.tp3.libs;

public class Position {
    private int column;
    private int row;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Position{" +
                "column=" + column +
                ", row=" + row +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position)) return false;
        Position cellObj = (Position) obj;
        return cellObj.getRow() == this.getRow() && cellObj.getColumn() == this.getColumn();
    }
}

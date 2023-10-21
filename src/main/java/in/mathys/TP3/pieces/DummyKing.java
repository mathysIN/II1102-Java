package in.mathys.TP3.pieces;

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

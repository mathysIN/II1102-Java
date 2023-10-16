package org.example.tp3;

import org.example.tp3.libs.*;
import org.example.tp3.pieces.*;

import java.util.*;
import java.util.stream.Stream;

public class Chess {
    private Scanner scanner = new Scanner(System.in);
    private Cell[][] board;
    private Player[] players = new Player[2];
    private Player currentPlayer;
    private King[] kings = new King[2];
    private Piece selectedPiece;
    private final static List<String> playerColors = new ArrayList() {
        {
            add(Color.PURPLE);
            add(Color.YELLOW);
        }
    };

    public void play() {
        createPlayers();
        System.out.println(Color.BOLD + Color.BLUE + "C'est parti !".toUpperCase() + Color.RESET);
        initialiseBoard();
        while (true) {
            String move;
            System.out.println("\nC'est au tour de " + Color.BOLD + currentPlayer.getName() + " de jouer !");
            boolean firstLoop = true;
            do {
                printBoard();
                if(!firstLoop) System.out.println(Color.BOLD + Color.RED + "Mauvais move" + Color.RESET);
                if(firstLoop) firstLoop = false;
                move = askMove();
            }
            while (!isValidMove(move));
            selectedPiece = null;
            updateBoard(move);
            switchPlayer();
            State state = isCheckMate(kings[currentPlayer.getColor()]);
            if(state == State.CHECK) {
                System.out.println(Color.BOLD + Color.YELLOW + "Echec !" + Color.RESET);
            }
            if(state == State.CHECKMATE) {
                // printBoard();
                // System.out.flush();
                // FIXME: is printed too late

                System.out.println();
                System.out.println(Color.BOLD + Color.YELLOW + "Echec et mat!" + Color.RESET);
                System.out.println(players[getOtherColor(currentPlayer.getColor())].getName() + " gagne la partie !");
//                break;
            }
        }
    }

    private void createPlayers() {
        for (int i = 0; i <= 1; i++) {
            String playerOneName = askString(Color.BOLD + getPlayerRenderColor(i) + "Joueur " + (i + 1) + Color.RESET + ", choisis ton blaze");
            players[i] = new Player(playerOneName, i);
            System.out.println("Wesh " + players[i].getName());
            System.out.println();
        }

        currentPlayer = players[0];
    }

    private void initialiseBoard() {
        board = new Cell[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Cell(new Position(i, j));
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                board[j][i == 0 ? 6 : 1].setPiece(new Pion(board[j][i == 0 ? 6 : 1].getPosition(), i));
            }
            for (int j = 0; j < 2; j++) {
                board[j == 0 ? 0 : 7][i == 0 ? 7 : 0].setPiece(new Rook(board[j == 0 ? 0 : 7][i == 0 ? 7 : 0].getPosition(), i));
                board[j == 0 ? 1 : 6][i == 0 ? 7 : 0].setPiece(new Knight(board[j == 0 ? 1 : 6][i == 0 ? 7 : 0].getPosition(), i));
                board[j == 0 ? 2 : 5][i == 0 ? 7 : 0].setPiece(new Bishop(board[j == 0 ? 2 : 5][i == 0 ? 7 : 0].getPosition(), i));
            }

            board[3][i == 0 ? 7 : 0].setPiece(new Queen(new Position(3, i == 0 ? 7 : 0), i));
            King king = new King(new Position(4, i == 0 ? 7 : 0), i);
            kings[i] = king;
            board[4][i == 0 ? 7 : 0].setPiece(king);
        }
    }

    private void printBoard() {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            Cell[] column = board[i];
            System.out.print(Color.BOLD);
            Set<Cell> moves = selectedPiece != null ? getPotentialMoves(selectedPiece) : null;
            for (int j = 0; j < column.length; j++) {
                Cell cell = board[j][i];
                if(cell == null) continue;
                if(j == 0) System.out.print((8 - i) + " ");
                if(moves != null && moves.contains(cell)) {
                    System.out.print(Color.BACKGROUND_YELLOW);
                }
                else {
                    System.out.print((i % 2 == 0 ? j % 2 == 0 : j % 2 == 1) ? Color.BACKGROUND_WHITE : Color.BACKGROUND_BLACK);
                }
                System.out.print(" ");
                System.out.print((cell.isEmpty() ? " " : cell.getPiece().toString()));
                System.out.print(" ");
            }
            System.out.print(Color.RESET);
            if(i == 1) {
                System.out.print("\t\t");
                System.out.print(Color.BOLD + "Score :");
            }
            if(i == 2 || i == 4) {
                Player tempPlayer = players[i == 2 ? 0 : 1];
                System.out.print("\t\t\t");
                System.out.print(Color.BOLD + tempPlayer.getRenderColor() + "\uD83D\uDC64");
                System.out.print(" ");
                System.out.print(tempPlayer.getName());
            }
            if(i == 3 || i == 5) {
                Player tempPlayer = players[i == 3 ? 0 : 1];
                System.out.print("\t\t\t↳ ");
                System.out.print(Color.BOLD);
                System.out.print(tempPlayer.getEatenPieces().stream().map(piece -> piece.getScore()).reduce(0, Integer::sum) + " pts");
                System.out.print(" ");
                if(tempPlayer.getEatenPieces().size() > 0) {
                    System.out.print("-");
                    System.out.print(" ");
                    System.out.print(String.join(" ", tempPlayer.getEatenPieces().stream().map(piece -> String.valueOf(piece.getIcon())).toList()));
                }
            }
            System.out.println();
        }
        System.out.print("  " + Color.BOLD);
        for (int j = 0; j < 8; j++) {
            System.out.print(" " + ((char) (Chars.A + j)) + " ");
        }
        System.out.println();
        System.out.println(Color.RESET);
    }

    private String askMove() {
        boolean loop = true;
        boolean firstLoop = true;
        String response = null;
        char column;
        int line;
        char column2;
        int line2;
        while(loop) {
            try {
                if(!firstLoop) System.out.println(Color.BOLD + Color.RED + "Mauvais move" + Color.RESET);
                firstLoop = false;
                if(isCheckMate(kings[currentPlayer.getColor()]) == State.CHECK) {
                    System.out.println("Echec !");
                }
                response = askString(currentPlayer.getName() + ", ton move ? (Un mouvement comme 'B8C8' ou 'C5H7')").toUpperCase();
                if(response.length() != 4) continue;

                column = response.charAt(0);
                line = Integer.valueOf(String.valueOf(response.charAt(1)));
                if(column < Chars.A || column > Chars.H) continue;
                if(line < 1 || line > 8) continue;

                column2 = response.charAt(2);
                line2 = Integer.valueOf(String.valueOf(response.charAt(3)));
                if(column2 < Chars.A || column2 > Chars.H) continue;
                if(line2 < 1 || line2 > 8) continue;
                loop = false;
            }
            catch (Exception e) {}
        }
        return response;
    }

    private boolean isValidMove(String move) {
        Cell[] cells = getCellsFromMove(move);
        Cell cell = cells[0];
        Cell newCell = cells[1];
        if(cell.isEmpty()) {
            System.out.println(Color.BOLD + Color.RED + "Pas de pièces");
            return false;
        };
        Piece piece = cell.getPiece();
        selectedPiece = piece;

        if(piece.getColor() != currentPlayer.getColor()) {
            System.out.println(Color.BOLD + Color.RED + "Pas ta pièce");
            return false;
        };
        Set<Cell> moves = getPotentialMoves(piece);

        if(!moves.contains(newCell)) return false;

        King king = kings[currentPlayer.getColor()];

        if(isFuturCheckMate(king, cell, newCell)) return false;

        return true;
    }

    private Set<Cell> getPotentialMoves(Piece piece) {
        Position position = piece.getPosition();
        Set<Cell> moves = new HashSet<>();

        if(piece instanceof Pion || piece instanceof DummyKing) {
            Cell potentialCell;
            // Vers l'avant, 1 case
            potentialCell = getCell(position.getColumn(), position.getRow() + piece.forward(1));
            if(potentialCell != null) {
                if(potentialCell.isEmpty()) moves.add(potentialCell);
            }

            // Vers l'avant, 1 case
            if(!piece.isHasMovedOnce()) {
                potentialCell = getCell(position.getColumn(), position.getRow() + piece.forward(2));
                if(potentialCell != null) {
                    if (potentialCell.isEmpty()) moves.add(potentialCell);
                }
            }

            // Diagonale droite gauche, manger
            for (int i = 0; i < 2; i++) {
                int colOffset = i == 0 ? -1 : 1;
                potentialCell = getCell(position.getColumn() + colOffset, position.getRow() + piece.forward(1));
                if(potentialCell != null) {
                    if(potentialCell != null && !potentialCell.isEmpty()) moves.add(potentialCell);
                }
            }
        }

        if(piece instanceof Bishop || piece instanceof Queen || piece instanceof DummyKing) {
            Cell potentialCell;
            // Diagonale dans les 4 directions
            for (int i = 0; i < 4; i++) {
                int colOffset = 0;
                int rowOffset = 0;
                while (true) {
                    if((i & 2) == 0) {
                        colOffset += 1;
                        rowOffset += i % 2 == 0 ? 1 : -1;
                    }
                    else {
                        colOffset += -1;
                        rowOffset += i % 2 == 1 ? 1 : -1;
                    }
                    potentialCell = getCell(position.getColumn() + colOffset, position.getRow() + rowOffset);
                    if (potentialCell != null) {
                        if (potentialCell.isEmpty()) moves.add(potentialCell);
                        else {
                            if (potentialCell.getPiece().getColor() != piece.getColor()) {
                                moves.add(potentialCell);
                            };
                            break;
                        }
                    }
                    else break;
                }
            }
        }

        if(piece instanceof Rook || piece instanceof Queen || piece instanceof DummyKing) {
            Cell potentialCell;
            // Ligne dans les 4 directions
            for (int i = 0; i < 4; i++) {
                int colOffset = 0;
                int rowOffset = 0;
                while (true) {
                    if((i & 2) == 0) {
                        colOffset += i % 2 == 0 ? 1 : -1;
                    }
                    else {
                        rowOffset += i % 2 == 0 ? 1 : -1;
                    }
                    potentialCell = getCell(position.getColumn() + colOffset, position.getRow() + rowOffset);
                    if (potentialCell != null) {
                        if (potentialCell.isEmpty()) moves.add(potentialCell);
                        else {
                            if (potentialCell.getPiece().getColor() != piece.getColor()) {
                                moves.add(potentialCell);
                            };
                            break;
                        }
                    }
                    else break;
                }
            }
        }

        if(piece instanceof Knight || piece instanceof DummyKing) {
            Cell potentialCell;
            // Ligne dans les 4 directions
            for (int i = 0; i < 4; i++) {
                int colOffset = 0;
                int rowOffset = 0;
                for (int j = 0; j < 2; j++) {
                    if((i & 2) == 0) {
                        colOffset = i % 2 == 0 ? 2 : -2;
                        rowOffset = j % 2 == 0 ? 1 : -1;
                    }
                    else {
                        colOffset = j % 2 == 0 ? 1 : -1;
                        rowOffset = i % 2 == 0 ? 2 : -2;
                    }
                    potentialCell = getCell(position.getColumn() + colOffset, position.getRow() + rowOffset);
                    if (potentialCell != null) {
                        if (potentialCell.isEmpty()) moves.add(potentialCell);
                        else if (potentialCell.getPiece().getColor() != piece.getColor()) {
                            moves.add(potentialCell);
                            break;
                        };
                    }
                    else break;
                }
            }
        }

        if(piece instanceof King && !(piece instanceof DummyKing)) {
            Cell potentialCell;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    potentialCell = getCell(position.getColumn() + i -1 , position.getRow() + j - 1);
                    if(potentialCell != null) {
                        if(potentialCell.getPosition().equals(position)) continue;
                        if(!potentialCell.isEmpty() && potentialCell.getPiece().getColor() == currentPlayer.getColor()) continue;
                        if(isCheckMate(new King(potentialCell.getPosition(), piece.getColor())) != State.NONE) continue;
                        moves.add(potentialCell);
                    }
                }
            }
        }
        return moves;
    }

    private Cell getCell(int col, int row) {
        try {
//            System.out.println("got" + board[col][row].getPosition().getColumn() + " " + board[col][row].getPosition().getRow());
            return board[col][row];
        }
        catch (Exception e) {
            return null;
        }
    }

    private void updateBoard(String move) {
        Cell[] cells = getCellsFromMove(move);
        Cell cell = cells[0];
        Cell newCell = cells[1];

        if(!newCell.isEmpty()) {
            currentPlayer.addEatenPiece(newCell.getPiece());
        }

        cell.getPiece().setPosition(newCell.getPosition());
        newCell.setPiece(cell.getPiece());
        cell.setPiece(null);
    }

    private void switchPlayer() {
        currentPlayer = players[currentPlayer.getColor() == 0 ? 1 : 0];
    }

    private String askString(String message) {
        System.out.print(message + "\n> ");
        return scanner.next();
    }

    public static String getPlayerRenderColor(int color) {
        return playerColors.get(color);
    }

    public static int letterMoveToNumber(char ch) {
        return ((int) ch) - Chars.A + 1;
    }

    public int getOtherColor(int color) {
        return color ^ 1;
    }

    public Cell[] getCellsFromMove(String move) {
        Cell[] cells = new Cell[2];
        cells[0] = getCell(letterMoveToNumber(move.charAt(0)) - 1, 8 - Integer.valueOf(String.valueOf(move.charAt(1))));
        cells[1] = getCell(letterMoveToNumber(move.charAt(2)) - 1, 8 - Integer.valueOf(String.valueOf(move.charAt(3))));
        return cells;
    }

    private State isCheckMate(King king) {
        return isCheckMate(king, 0);
    }

    private State isCheckMate(King king, int level) {
        State state;
        Set<Cell> moves = getPotentialMoves(new DummyKing(king));
        Stream<Cell> movesStream = moves.stream().filter(cell -> !cell.isEmpty());
        movesStream = movesStream.filter(cell -> cell.getPiece().getColor() != king.getColor());
        movesStream = movesStream.filter(cell -> {
            if(cell.getPiece() instanceof King) return false;
            return getPotentialMoves(cell.getPiece()).stream().filter(c -> !c.isEmpty()).toList().size() > 0;
        });

        List<Cell> kingAttackedBy = movesStream.toList();

        if(kingAttackedBy.size() > 0) {
            boolean nahHeTweaking = false;
            if (level == 0) {
                for (int i = 0; i < board.length; i++) {
                    if (nahHeTweaking) break;
                    Cell[] column = board[i];
                    for (int j = 0; j < column.length; j++) {
                        if(nahHeTweaking) break;
                        Cell cell = board[j][i];
                        if (cell.isEmpty()) continue;
                        Piece piece = cell.getPiece();
                        if (piece.getColor() != king.getColor()) continue;
                        if (piece instanceof King) continue;
                        for (Cell potentialMove : getPotentialMoves(piece)) {
                            nahHeTweaking = !isFuturCheckMate(king, cell, potentialMove);
                            if(nahHeTweaking) break;
                        }
                    }
                }
            }
            state = nahHeTweaking ? State.CHECK : State.CHECKMATE;
        }
        else state = State.NONE;
        return state;
    }

    private boolean isFuturCheckMate(King king, Cell cell, Cell potentialMove) {
        Position position = cell.getPosition();
        Piece piece = cell.getPiece();
        Piece oldPiece = board[potentialMove.getPosition().getColumn()][potentialMove.getPosition().getRow()].getPiece();
        if(piece instanceof Queen) {
            System.out.println();
        }
        board[position.getColumn()][position.getRow()].setPiece(null);
        board[potentialMove.getPosition().getColumn()][potentialMove.getPosition().getRow()].setPiece(piece);

        boolean nahHeTweaking = isCheckMate(king, 1) == State.NONE ;

        board[position.getColumn()][position.getRow()].setPiece(piece);
        board[potentialMove.getPosition().getColumn()][potentialMove.getPosition().getRow()].setPiece(oldPiece);
        return !nahHeTweaking;
    }

    private Cell[][] cloneBoard(Cell[][] board) {
        Cell[][] clonedBoard = new Cell[8][8];
        for (int i = 0; i < board.length; i++) {
            Cell[] column = board[i];
            for (int j = 0; j < column.length; j++) {
                clonedBoard[i][j] = new Cell(new Position(column[j].getPosition().getColumn(), column[j].getPosition().getRow()));
                clonedBoard[i][j].setPiece(column[j].getPiece());
            }
        }
        return clonedBoard;
    }
}

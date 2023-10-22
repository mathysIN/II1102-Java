package in.mathys.tp3;

import in.mathys.tp3.libs.*;
import in.mathys.tp3.libs.text.Chars;
import in.mathys.tp3.libs.text.Color;
import in.mathys.tp3.libs.text.Icons;
import in.mathys.tp3.pieces.*;
import in.mathys.tp3.moves.Castling;
import in.mathys.tp3.moves.EnPassant;
import in.mathys.tp3.moves.Move;
import in.mathys.tp3.moves.Promotion;

import java.util.*;
import java.util.stream.Stream;

// FIXME: Il y a peut être un problème de recursion de function au test des echecs
// Le programme semble rammer lors d'un echec : La recursion va potentiellement 1 niveau trop bas mais j'ai ni le temps
// ni le budget de continuer à développer ce jeu

public class Chess {
    private Scanner scanner = new Scanner(System.in);
    private Cell[][] board;
    private Set<Cell> highlightedCells = new HashSet<Cell>();
    private Player[] players = new Player[2];
    private Player currentPlayer;
    private King[] kings = new King[2];
    private Position dummyPosition = new Position(0, 0);
    private ArrayList<Piece> availablePromotions = new ArrayList<>() {
        {
            add(new Queen(dummyPosition, 0));
            add(new Rook(dummyPosition, 0));
            add(new Knight(dummyPosition, 0));
            add(new Bishop(dummyPosition, 0));
            add(new Pawn(dummyPosition, 0));
        }
    };
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

            highlightedCells.clear();
            updateBoard(move);
            switchPlayer();
            King king = kings[currentPlayer.getColor()];
            State state = isCheckMate(king);

            if(state == State.NONE && isPat(currentPlayer.getColor())) {
                printBoard();
                System.out.flush();
                System.out.println();
                System.out.println(Color.BOLD + Color.YELLOW + "Pat !" + Color.RESET);
                System.out.println("Egalité");
                break;
            }
            if(state == State.CHECK) {
                System.out.println(Color.BOLD + Color.YELLOW + "Echec !" + Color.RESET);
            }
            if(state == State.CHECKMATE) {
                printBoard();
                System.out.flush();

                System.out.println();
                System.out.println(Color.BOLD + Color.YELLOW + "Echec et mat!" + Color.RESET);
                System.out.println(players[getOtherColor(currentPlayer.getColor())].getName() + " gagne la partie !");
                break;
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
        for (int column = 0; column < 8; column++) {
            for (int row = 0; row < 8; row++) {
                board[column][row] = new Cell(new Position(column, row));
            }
        }

        for (int color = 0; color < 2; color++) {
            for (int column = 0; column < 8; column++) {
                board[column][color == 0 ? 6 : 1].setPiece(new Pawn(board[column][color == 0 ? 6 : 1].getPosition(), color));
            }
            for (int column = 0; column < 2; column++) {
                board[column == 0 ? 0 : 7][color == 0 ? 7 : 0].setPiece(new Rook(board[column == 0 ? 0 : 7][color == 0 ? 7 : 0].getPosition(), color));
                    board[column == 0 ? 1 : 6][color == 0 ? 7 : 0].setPiece(new Knight(board[column == 0 ? 1 : 6][color == 0 ? 7 : 0].getPosition(), color));
                board[column == 0 ? 2 : 5][color == 0 ? 7 : 0].setPiece(new Bishop(board[column == 0 ? 2 : 5][color == 0 ? 7 : 0].getPosition(), color));
            }

            board[3][color == 0 ? 7 : 0].setPiece(new Queen(new Position(3, color == 0 ? 7 : 0), color));
            King king = new King(new Position(4, color == 0 ? 7 : 0), color);
            kings[color] = king;
            board[4][color == 0 ? 7 : 0].setPiece(king);
        }
    }

    private void printBoard() {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            Cell[] column = board[i];
            System.out.print(Color.BOLD);
            for (int j = 0; j < column.length; j++) {
                Cell cell = board[j][i];
                if(cell == null) continue;
                if(j == 0) System.out.print((8 - i) + " ");
                if(highlightedCells.stream().filter(c -> c.getPosition().equals(cell.getPosition())).findFirst().orElseGet(() -> null) != null) {
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
                System.out.print(Color.BOLD + tempPlayer.getRenderColor() + Icons.SHIELD);
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
        highlightedCells.clear();
        Cell[] cells = getCellsFromMove(move);
        Cell cell = cells[0];
        Cell newCell = cells[1];
        if (cell.isEmpty()) {
            System.out.println(Color.BOLD + Color.RED + "Pas de pièces");
            return false;
        }
        ;
        Piece piece = cell.getPiece();

        if (piece.getColor() != currentPlayer.getColor()) {
            System.out.println(Color.BOLD + Color.RED + "Pas ta pièce");
            return false;
        }
        ;
        Set<Cell> moves = getPotentialMoves(piece);
        highlightedCells = moves;

        Cell selectedMove = moves.stream().filter(cell1 -> cell1.getPosition().equals(newCell.getPosition())).findFirst().orElseGet(() -> null);
        if(selectedMove == null) return false;

        return true;
    }

    private Set<Cell> getPotentialMoves(Piece piece) {
        return getPotentialMoves(piece, 0);
    }

    private Set<Cell> getPotentialMoves(Piece piece, int level) {
        Position position = piece.getPosition();
        Set<Cell> moves = new HashSet<>();
        Cell cell = getCell(position.getColumn(), position.getRow());

        if(piece instanceof Pawn || piece instanceof DummyKing) {
            Cell potentialCell;
            // Vers l'avant, 1 case
            potentialCell = getCell(position.getColumn(), position.getRow() + piece.forward(1));
            if(potentialCell != null) {
                if(potentialCell.isEmpty()) {
                    moves.add(potentialCell);

                    // Vers l'avant, 2 case
                    if(!piece.hasMovedOnce()) {
                        potentialCell = getCell(position.getColumn(), position.getRow() + piece.forward(2));
                        if(potentialCell != null) {
                            if (potentialCell.isEmpty()) moves.add(potentialCell);
                        }
                    }
                }

            }

            // Diagonale droite gauche, manger
            for (int i = 0; i < 2; i++) {
                int colOffset = i == 0 ? 1 : -1;
                potentialCell = getCell(position.getColumn() + colOffset, position.getRow() + piece.forward(1));
                if(potentialCell != null) {
                    if(!potentialCell.isEmpty() && potentialCell.getPiece().getColor() != piece.getColor()) moves.add(potentialCell);
                }
            }

            // En passant
            for (int i = 0; i < 2; i++) {
                int colOffset = i == 0 ? 1 : -1;
                potentialCell = getCell(position.getColumn() + colOffset, position.getRow() + piece.forward(1));
                if(potentialCell != null) {
                    Cell potentialCellEnPassant = getCell(position.getColumn() + colOffset, position.getRow());
                    if(potentialCellEnPassant != null && !potentialCellEnPassant.isEmpty() && potentialCellEnPassant.getPiece().getColor() != piece.getColor()) {
                        if(potentialCellEnPassant.getPiece() instanceof Pawn && ((Pawn) potentialCellEnPassant.getPiece()).canBeEnPassant())
                        moves.add(new EnPassant(potentialCell.getPosition(), potentialCellEnPassant));
                    };
                }
            }

            // Promotions
            if(!(piece instanceof DummyKing)) {
                Set<Cell> tempMoves = new HashSet<>();
                tempMoves.addAll(moves);
                for(Cell move : tempMoves) {
                    int row = move.getPosition().getRow();
                    if(piece.getColor() == 0 ? row == 0 : row == 7 ) {
                        moves.remove(move);
                        moves.add(new Promotion(move.getPosition()));
                    }
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
            // 8 cases adjacentes
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    potentialCell = getCell(position.getColumn() + i -1 , position.getRow() + j - 1);
                    if(potentialCell != null) {
                        if(potentialCell.getPosition().equals(position)) continue;
                        if(!potentialCell.isEmpty() && potentialCell.getPiece().getColor() == currentPlayer.getColor()) continue;

                        moves.add(potentialCell);
                    }
                }
            }

            // Rock
            if(!piece.hasMovedOnce()) {
                for (int i = 0; i < 2; i++) {
                    Cell tempCell = getCell(i == 0 ? 0 : 7, position.getRow());
                    if(tempCell.isEmpty()) continue;
                    if(!(tempCell.getPiece() instanceof Rook)) continue;
                    Rook rook = null;
                    for (int j = 1; true; j++) {
                        Cell _tempCell = getCell(position.getColumn() + (i == 0 ? j : -j), position.getRow());
                        if(_tempCell.isEmpty()) continue;

                        if(_tempCell.getPiece().getColor() == currentPlayer.getColor() && _tempCell.getPiece() instanceof Rook) {
                            rook = (Rook) _tempCell.getPiece();
                            break;
                        }
                        break;
                    }
                    if(rook == null) continue;
                    if(rook.hasMovedOnce()) continue;
                    moves.add(new Castling(new Position(position.getColumn() + (i == 0 ? 2 : -2), position.getRow()), getCell(rook.getPosition()), getCell(position.getColumn() + (i == 0 ? 1 : -1), position.getRow())));
                }
            }




        }

        if(!(piece instanceof DummyKing) && level == 0) {
            Set<Cell> tempMoves = new HashSet<>();
            tempMoves.addAll(moves);

            for(Cell move : tempMoves) {
                King king = kings[currentPlayer.getColor()];
                boolean c = isFuturCheckMate(king, cell, move, 0);
                if(c) moves.remove(move);
            }
        }


        return moves;
    }

    private Cell getCell(Position position) {
        return getCell(position.getColumn(), position.getRow());
    }

    private Cell getCell(int col, int row) {
        try {
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

        Cell _move = getPotentialMoves(cell.getPiece()).stream().filter(cell1 -> cell1.getPosition().equals(newCell.getPosition())).findFirst().orElseGet(null);
        if(_move == null) throw new RuntimeException("Move should not be null");

        Piece piece = cell.getPiece();
        cell.getPiece().setPosition(newCell.getPosition());
        cell.setPiece(null);
        newCell.setPiece(piece);
        piece.setHasMovedOnce(true);

        if(_move instanceof Move) {
            if(_move instanceof EnPassant) {
                EnPassant enPassant = (EnPassant) _move;
                currentPlayer.addEatenPiece(enPassant.getAttackedPawn().getPiece());
                enPassant.getAttackedPawn().setPiece(null);
            }
            if(_move instanceof Promotion) {
                System.out.println("Promotion time!!! Choisis une promotion pour ton pion");
                for (int i = 0; i < availablePromotions.size(); i++) {
                    System.out.print((i + 1) + " - ");
                    System.out.print(availablePromotions.get(i).getDisplayName());
                    System.out.print("\n");
                }

                System.out.println();
                Piece promotedPiece = null;
                while (true) {
                    try {
                        int output = askInt("");
                        promotedPiece = availablePromotions.get(output - 1);
                        if(promotedPiece != null) break;
                    }
                    catch (RuntimeException e) {}
                }

                try {
                    newCell.setPiece(promotedPiece.getClass().cast(promotedPiece.getClass().getConstructors()[0].newInstance(newCell.getPosition(), currentPlayer.getColor())) );
                }

                catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Une erreur a eu lieu lors de la promotion de la pièce");
                }

                System.out.print("Très bien, tu as selectionné " + promotedPiece.getDisplayName() + " !");

            }
            if(_move instanceof Castling) {
                Castling castling = (Castling) _move;
                Cell targetedRook = castling.getTargetedRook();
                castling.getNewRookCell().setPiece(targetedRook.getPiece());
                targetedRook.setPiece(null);
            }
        }

    }

    private void switchPlayer() {
        currentPlayer = players[currentPlayer.getColor() == 0 ? 1 : 0];
    }

    private String askString(String message) {
        System.out.print(message + "\n> ");
        return scanner.next();
    }

    private int askInt(String message) {
        System.out.print(message + "\n> ");
        int output;
        while (true) {
            try {
                output = scanner.nextInt();
                break;
            }
            catch (Exception e) {
                scanner.nextLine();

                System.out.print("> "); // ?
            }
        }
        return output;
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
            int t = getPotentialMoves(cell.getPiece(), 1).stream().filter(c -> c.getPosition().equals(king.getPosition())).toList().size();
            return t > 0;
        });

        List<Cell> kingAttackedBy = movesStream.toList();

        if(kingAttackedBy.size() > 0) {
            boolean nahHeTweaking = false;
            if (level < 1) {
                for (int i = 0; i < board.length; i++) {
                    if (nahHeTweaking) break;
                    Cell[] column = board[i];
                    for (int j = 0; j < column.length; j++) {
                        if(nahHeTweaking) break;
                        Cell cell = board[j][i];
                        if (cell.isEmpty()) continue;
                        Piece piece = cell.getPiece();
                        if (piece.getColor() != king.getColor()) continue;
                        Set<Cell> potentialMoves =  getPotentialMoves(piece);
                        for (Cell potentialMove : potentialMoves) {
                            nahHeTweaking = !isFuturCheckMate(king, cell, potentialMove, level);
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

    private boolean isFuturCheckMate(King king, Cell cell, Cell potentialMove, int level) {
        Position position = cell.getPosition();
        Piece piece = cell.getPiece();
        Piece oldPiece = board[potentialMove.getPosition().getColumn()][potentialMove.getPosition().getRow()].getPiece();

        board[position.getColumn()][position.getRow()].setPiece(null);
        board[potentialMove.getPosition().getColumn()][potentialMove.getPosition().getRow()].setPiece(piece);

        boolean nahHeTweaking = isCheckMate(king, level + 1) == State.NONE ;

        board[position.getColumn()][position.getRow()].setPiece(piece);
        board[potentialMove.getPosition().getColumn()][potentialMove.getPosition().getRow()].setPiece(oldPiece);
        return !nahHeTweaking;
    }

    private boolean isPat(int color) {
        for (int i = 0; i < board.length; i++) {
            Cell[] column = board[i];
            for (int j = 0; j < column.length; j++) {
                Cell cell = column[j];
                if(cell.isEmpty()) continue;
                if(cell.getPiece().getColor() != color) continue;
                if(getPotentialMoves(cell.getPiece()).size() != 0) return false;
            }
        }
        return true;
    }
}

package in.mathys.TP2;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static class Program {
        private String name;
        private Runnable runnable;
        public Program(String name, Runnable runnable) {
            this.name = name;
            this.runnable = runnable;
        }

        public boolean hasValidProgram() {
            return runnable != null;
        }

        public Runnable getRunnable() {
            return runnable;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {

        List<Program> programs = new ArrayList<>() {
            {
                add(new Program("Discriminant", Main::discriminant));
                add(new Program("Parité d’un nombre", Main::parite));
                add(new Program("Calcul d’extremum", Main::extremum));
                add(new Program("Égalité entre chaînes de caractères", () -> {
                    System.out.print("Entre une première chaine de charactères\n> ");
                    String chaine1 = scanner.next();
                    System.out.print("Entre une seconde chaine de charactères\n> ");
                    String chaine2 = scanner.next();
                    System.out.println("Ces 2 chaines " + (Main.equalString (chaine1, chaine2) ? "sont similaires" : "ne sont pas similaires") + ".");;
                }));
                add(new Program("Factorielle", Main::factorielle));
                add(new Program("Compte à rebours", Main::compteARebours));
                add(new Program("Valeurs de carrés", Main::carres));
                add(new Program("Table multiplicaton", Main::tableMultiplication));
                add(new Program("Division d’entiers", Main::division));
                add(new Program("Règle graduée", Main::regleMan));
                add(new Program("Nombres premiers", Main::nombrePremier));
                add(new Program("Manipulations sur un tableau", Main::initialisationTableau));
            }
        };

        System.out.println();
        List<Program> workingPrograms = programs.stream().filter(program -> {
            if(!program.hasValidProgram()) {
                System.out.println("Ignoring program '" + program.getName() + "' since there is no runnable defined..." );
            }
            return program.hasValidProgram();
        }).toList();
        System.out.println();

        System.out.println("Programmes :\n");

        for (int i = 0; i < workingPrograms.size(); i++) {
            Program program = workingPrograms.get(i);
            System.out.println(i + 1 + "\t" + "- " + "\t" + program.getName());
        }
        System.out.println();

        int value;
        while (true) {
            try {
                System.out.print("\nChoisis un programme\n> ");
                value = scanner.nextInt() - 1;
                if(workingPrograms.get(value) != null) {
                    break;
                }
                System.err.println("Erreur : Programme invalide");
            }
            catch (Exception e) {
                System.err.println("Erreur : " + e.getMessage());
                scanner.nextLine();
            }
        }

        Program program = workingPrograms.get(value);

        System.out.println("\nDémarrage du programme '" + program.getName() + "'...\n\n");
        program.getRunnable().run();
    }

    public static void discriminant() {
        System.out.print("Quelle est la valeur de a ?\n> ");
        int a = scanner.nextInt();
        System.out.print("Quelle est la valeur de b ?\n> ");
        int b = scanner.nextInt();
        System.out.print("Quelle est la valeur de c ?\n> ");
        int c = scanner.nextInt();
        int delta = (int) (Math.pow(b, 2) - 4 * a * c);
        System.out.println("delta = " + delta);
        if (delta < 0) {
            System.out.println("Ce polynome n’a pas de racine reelle");
        } else {
            if (delta == 0) {
                System.out.println(-b / (2 * a));
            } else {
                System.out.println((-b + Math.sqrt(delta)) / 2 * a);
                System.out.println((-b - Math.sqrt(delta)) / 2 * a);
            }
        }
    }

    public static void parite() {
        System.out.print("Saisis un entier\n> ");
        int entier = scanner.nextInt();
        System.out.println();

        if (entier % 2 == 0) {
            System.out.println("Le chiffre " + entier + " est pair.");
        } else {
            System.out.println("Le chiffre " + entier + " est impair.");
        }
    }

    public static void factorielle() {

        System.out.print("Saisis un entier positif ou null\n> ");
        int n = scanner.nextInt();
        int factorielle = 1;
        for (int i = 0; i <= n; i++) {
            factorielle *= i;
        }

        System.out.println(n + "! = " + factorielle);
    }

    public static void carres() {
        System.out.println("x\t\tx^2");
        System.out.println("----------------");

        for (int x = 1; x <= 10; x++) {
            int xCarre = x * x;
            System.out.println(x + "\t\t" + xCarre);
        }
    }

    public static void extremum() {
        int maximum = max();
        System.out.println("\nLe maximum est : " + maximum);
        int minimum = min();
        System.out.println("\nLe minimum est : " + minimum);
    }

    public static int max() {
        System.out.print("Saisis le premier entier\n> ");
        int premierEntier = scanner.nextInt();

        System.out.print("\nSaisis le deuxième entier\n> ");
        int deuxiemeEntier = scanner.nextInt();

        return premierEntier > deuxiemeEntier ? premierEntier : deuxiemeEntier;
    }

    public static int min() {
        System.out.print("Saisis le premier entier\n> ");
        int premierEntier = scanner.nextInt();

        System.out.print("\nSaisis le deuxième entier\n> ");
        int deuxiemeEntier = scanner.nextInt();

        return premierEntier < deuxiemeEntier ? premierEntier : deuxiemeEntier;
    }

    public static void compteARebours() {
        for (int i = 10; i >= 0; i--) {
            System.out.println("[\uD83D\uDCA3\uFE0F] " + i );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("[\uD83D\uDCA5] BOOM !");
    }

    public static void division() {
        int numerateur, denominateur;

        System.out.print("Saisis le numerateur\n> ");
        numerateur = scanner.nextInt();
        System.out.println();

        do {
            System.out.print("Saisis le denominateur (doit être différent de 0)\n> ");
            denominateur = scanner.nextInt();
            System.out.println();
        } while (denominateur == 0);

        double resultat = (double) numerateur / denominateur;

        System.out.println("Le résultat de la division est : " + resultat);
    }

    public static void tableMultiplication() {
        for (int i = 1; i <= 10; i++) {
            String chaineFinale = "";
            for (int y = 1; y <= 10; y++) {
                chaineFinale = chaineFinale + i * y + "\t";
            }
            System.out.println(chaineFinale);
        }
    }

    public static void regleMan() {
        int tailleRegle;
        do {
            System.out.print("Saisis la taille (doit être supéreur à 10)\n> ");
            tailleRegle = scanner.nextInt();
            System.out.println();
        } while (tailleRegle <= 10);
        String regleChaine = "|";
        String graduation = "0";
        for (int i = 0; i <= tailleRegle / 10; i++) {
            int y;
            boolean end = false;
            int espaceTaille = obtenirDizaine((i * 10)  + 10) - 1;
            for (y = 0; y <= 9; y++) {
                regleChaine = regleChaine + "-";
                if(10 - espaceTaille > y) {
                    graduation = graduation + " ";
                }
                if(i  >= tailleRegle / 10 && y >= tailleRegle % 10) {
                    end = true;
                    break;
                };
            }
            regleChaine = regleChaine + "|";
            graduation = graduation + ((i + (end ? 0 : 1)) * 10 + (end ? y : 0));
            if(end) break;
        }
        System.out.println(regleChaine);
        System.out.println(graduation);
    }

    public static int obtenirDizaine(int valeur) {
        int chiffre = 0;
        int magnitude = 1;

        while (valeur >= magnitude) {
            chiffre++;
            magnitude *= 10;
        }

        return chiffre;
    }

    public static void nombrePremier() {
        boolean valid = false;
        int valeur = 0;
        while (!valid) {
            try {
                System.out.print("Donne un entier lol\n> ");
                valeur = scanner.nextInt();
                if (valeur < 0) {
                    valid = false;
                } else valid = true;
            } catch (Exception e) {
                valid = false;
            }
        }

        boolean premier = true;
        for (int i = 2; i < valeur; i++) {
            if (valeur % i == 0) {
                premier = false;
                break;
            }
        }

        if (premier) {
            System.out.println("Ton nombre est premier");
        } else {
            System.out.println("Ton nombre n'est pas premier");
        }
    }


    public static boolean equalString(String chaine1, String chaine2) {
        return chaine1.equals(chaine2);
    }

    public static void initialisationTableau() {
        final int TAILLE_TABLEAU = 5;
        int[] tableau = new int[TAILLE_TABLEAU];
        ArrayList tableauPair = new ArrayList();
        int max = 0;
        int min = 0;
        int sum = 0;
        for (int i = 0; i < tableau.length; i++) {
            System.out.print("Saisis un entier (" + (tableau.length - i) + " restants)\n> ");
            int entier = scanner.nextInt();
            if(entier > max) max = entier;
            if(entier < min) min = entier;
            if(entier % 2 == 0) tableauPair.add(entier);
            sum = sum + entier;
            tableau[i] = entier;
        }

//        max = Arrays.stream(tableau).max().getAsInt();
//        min = Arrays.stream(tableau).min().getAsInt();
//        sum = Arrays.stream(tableau).sum();

        int[] tableauInverse = new int[TAILLE_TABLEAU];
        String tableauInverseString = "[";
        for (int i = 0; i < tableau.length; i++) {
            tableauInverse[i] = tableau[tableau.length - i - 1];
            tableauInverseString = tableauInverseString + tableau[tableau.length - i - 1] + (i == tableau.length - 1 ? "" : ", ");
        }
        tableauInverseString = tableauInverseString + "]";

        System.out.println();
        System.out.println("MIN = " + min);
        System.out.println("MAX = " + max);
        System.out.println("SUM = " + sum);
        System.out.println("PAIRS = " + tableauPair);
        System.out.println("INVERSE = " + tableauInverseString);

    }
}

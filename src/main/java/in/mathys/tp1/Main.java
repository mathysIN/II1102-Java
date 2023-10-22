package in.mathys.tp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        volume();
    }

   public static void bruh() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nRentre un entier \n> ");
        int premierEntier = scanner.nextInt();
        System.out.print("\nRentre un entier\n> ");
        int deuxiemeEntier = scanner.nextInt();
        int somme = premierEntier + deuxiemeEntier;
        System.out.println("Le résultat est : " + somme);
   }

   public static void division() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nRentre un entier\n> ");
        float premierEntier = scanner.nextFloat();
        System.out.print("\nRentre un entier\n> ");
        float deuxiemeEntier = scanner.nextFloat();
        if(deuxiemeEntier == 0) {
            System.out.println("Impossible de diviser par 0");
            return;
        }
        float division = premierEntier / deuxiemeEntier;
        System.out.println("Le résultat est : " + division);
   }

   public static void volume() {
        List<Float> data = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

       for (int i = 0; i < 3; i++) {
           String bruh = "";
           switch (i) {
               case 0:
                   bruh = "hauteur";
                   break;
               case 1:
                   bruh = "largeur";
                   break;
               case 2:
                   bruh = "longeur";
                   break;
           }
           System.out.print("\nRentre la valeur de " + bruh + "\n> ");
           Float newValue = scanner.nextFloat();
           if(newValue < 0) {
               System.out.println("Valeur incorrecte");
               i--;
               continue;
           }
           data.add(newValue);
       }

        System.out.println("");
        System.out.println("Volume = " + data.get(0) * data.get(1) * data.get(2));
   }
}
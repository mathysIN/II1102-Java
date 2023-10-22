package in.mathys.quiz;

public class TP_Pratique_G_A {

    public static void main(String[] args) {
        System.out.println(Somme(4));
    }
    public static int Somme(int n) {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            if(i % 2 == 0) {
                sum = sum + i;
            }
            if(i % 5 == 0) {
                sum = sum + i;
            }
        }
        return sum;
    }
    public static void pair(int n) {
        for (int i = 0; i < n ; i++) {
            if(i % 2 == 0) {
                System.out.println(i);
                System.out.println();
            }
        }
    }



    public static void multiplication(int n) {
        for (int i = 1; i <= 10; i++) {
            System.out.println(n + "*" + i + " = " + n*i);
        }
    }
}

package in.mathys.tp6;
import java.time.Instant;
import java.time.Duration;
import java.util.Random;

public class Main {
	static Random random = new Random();
	static int SIZE = 10;

	public static void main (String[] args) {
		int[] tableau = initTableau(SIZE);
		mesure(() -> insertion(copy(tableau)), "Insertion");
		mesure(() -> selection(copy(tableau)), "Selection");
		mesure(() -> printTableau(selectionRec(copy(tableau))), "Selection recursive"); // java empeche une trop grosse recursion ?
	}

	public static void insertion (int[] tableau) {
		for(int i = 1; i < tableau.length; i++) {
			int j = i;
			while(j > 0 && tableau[j] < tableau[j-1]) {
				int temp = tableau[j];
				tableau[j] = tableau[j-1];
				tableau[j-1] = temp;
				j = j - 1;
			};	
		};
	}

	public static void selection (int[] tableau) {
		for(int i = 0; i < tableau.length; i++) {
			int minIndice = i;
			for(int y = i; y < tableau.length; y++) {
				int temp = tableau[y];
				if(tableau[temp] < tableau[minIndice]) minIndice = y; 
			}
			int temp = tableau[i];
			tableau[i] = tableau[minIndice];
			tableau[minIndice] = temp;
		}
	}

	public static int[] selectionRec (int[] tableau) {
		return selectionRec(tableau, 0);
	}

	// non fonctionnel
	public static int[] selectionRec (int[] tableau, int i) {
		if(tableau.length == i) {
			return tableau;
		}
		else {
			int minIndice = i;
                        for(int y = i; y < tableau.length; y++) {
                                int temp = tableau[y];
                                if(tableau[temp] < tableau[minIndice]) minIndice = y;
                        }
                        int temp = tableau[i];
                        tableau[i] = tableau[minIndice];
                        tableau[minIndice] = temp;
			return selectionRec(tableau, i + 1);
		}	
	}

	public static int[] initTableau(int size) {
		int[] tableau = new int[SIZE];
		for (int i = 0; i < tableau.length; i++) {
			tableau[i] = random.nextInt(SIZE);
		}
		return tableau;
	}

	public static void printTableau(int[] tableau) {
		for(int temp : tableau) {
			System.out.println(temp);	
		}
	}

	public static int[] copy(int[] tableau) {
		int[] copieTableau = new int[tableau.length];
		System.arraycopy(tableau, 0, copieTableau, 0, 3);
		return copieTableau;
	}

	public static void mesure(Runnable runnable, String title) {
		Instant start = Instant.now();
		runnable.run();
		Instant end = Instant.now();
		long duration = Duration.between(start, end).toMillis();
		System.out.println("Temps d'execution de " + title + " : " + duration + " ms (" + start + " - " + end + ")");
	}
}

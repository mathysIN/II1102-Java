// je sais pas j'essaye de coder en utilisant vim, c'est galère

package in.mathys.tp5;

import java.util.*;
import java.io.*;

public class Main {
public static void main(String[] args) {
// scanner va lire le contenu de fichier.csv
	Scanner scanner = null;
	try {
		scanner = new Scanner(new File("files/dpt2018.csv"));
	}
	catch (Exception e) {}
	StringBuilder stringBuilder = new StringBuilder();
	int invalid = 0;
	int[] sexs = new int[2];
	while (scanner.hasNextLine()) {
		String line = scanner.nextLine();
		String _line = line.toLowerCase();
		if(_line.contains("xxxx") && _line.contains("annais") || _line.contains("dpt")) invalid++;
		line.replaceAll("XXXX","-1");
		stringBuilder.append(line)
		.append("\n");
	}
	scanner.close();
	
	String data = stringBuilder.toString();
	String[] lines = data.split("\n");

	for(String line : lines) {
		String[] _line = line.split(";");
		if(_line[0].contains("1")) sexs[0]++;
		else if(_line[0].contains("2")) sexs[1]++;
	}

	System.out.println("Line count " + lines.length);
	System.out.println("Sample line : ");
	System.out.println(lines[0]);
	System.out.println(lines[5]);
	System.out.println();
	System.out.println("Invalid count " + invalid);	
	System.out.println("'Masculine' name count " + sexs[0] + " (" + (((float) sexs[1]/(float) lines.length)*100) + "%)");
	System.out.println("'Feminin' name count " + sexs[1] + " (" + (((float) sexs[0]/ (float) lines.length)*100) + "%)");
}
}


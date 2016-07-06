package tools;

import java.util.Scanner;

public class InstructionCorrector {

	public static void main(String[] args) {
		while (true) {
			Scanner sc = new Scanner(System.in);
			String[] command = sc.nextLine().split("");
			for (int i = 0; i < command.length; i++) {
				if (Integer.parseInt(command[i]) == 1)
					System.out.println(i);
			}
		}
	}

}

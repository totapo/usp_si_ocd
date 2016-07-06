package tools;

import java.util.Arrays;
import java.util.Scanner;

public class InstructionMaker {

	public static void main(String[] args) {

		while (true) {
			Scanner sc = new Scanner(System.in);
			String[] line = sc.nextLine().split(" ");
			int[] command = new int[line.length];
			for (int i = 0; i < command.length; i++) {
				command[i] = Integer.parseInt(line[i]);
			}
			Arrays.sort(command);
			String resp = "";
			int j = 0;
			for (int i = 0; i <= 29; i++) {
				if (j < command.length && command[j] == i) {
					resp += "1";
					j++;
				} else {
					resp += "0";
				}
			}
			System.out.println(resp);
		}

	}

}

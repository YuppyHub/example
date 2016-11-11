package com.tianbao54.io.listener;

import java.io.PrintWriter;
import java.util.Scanner;

public class KeyListener implements Runnable {

	private PrintWriter out;

	public KeyListener(PrintWriter out) {
		this.out = out;
	}

	
	@Override
	public void run() {

		Scanner scanner = new Scanner(System.in);

		while (scanner.hasNextLine()) {
			out.println(scanner.nextLine());
			out.flush();
		}
		
		scanner.close();
	}
}

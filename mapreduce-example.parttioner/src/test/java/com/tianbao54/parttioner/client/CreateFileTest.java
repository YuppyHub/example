package com.tianbao54.parttioner.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class CreateFileTest {
	
	private String [] arrays = {"135","136","137","138","139","150","159","182","183"};
	
	
	@Test
	public void testCreate () throws IOException {
		
		FileOutputStream fos = new FileOutputStream(new File ("c:/phone.txt"));
		
		for (int i = 0 ; i < 100000 ; i ++) {
			
			String preNum = arrays[(int)(Math.random() * 8)];
			
			String number = "";
			for (int j = 0; j < 7; j++) {
				int doub = (int) (Math.random() * 10);
				number += doub;
			}
			
			String phone = (preNum + number + (int)(Math.random() * 10));
			
			String line = phone + "," + (int)(Math.random() * 1024) + "," + (int)(Math.random() * 1024) + "\n";
			fos.write(line.getBytes());
		}
		fos.flush();
		fos.close();
	}
}

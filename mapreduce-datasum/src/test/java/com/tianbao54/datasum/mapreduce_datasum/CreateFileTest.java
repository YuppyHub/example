package com.tianbao54.datasum.mapreduce_datasum;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class CreateFileTest {

	
	@Test
	public void testCreate () throws IOException {
		
		FileOutputStream fos = new FileOutputStream(new File ("c:/phone.txt"));
		
		for (int i = 0 ; i < 100000 ; i ++) {
			String phone = "1326251300";
			phone = (phone + (int)(Math.random() * 10));
			
			String line = phone + "," + (int)(Math.random() * 1024) + "," + (int)(Math.random() * 1024) + "\n";
			fos.write(line.getBytes());
		}
		fos.flush();
		fos.close();
	}
}

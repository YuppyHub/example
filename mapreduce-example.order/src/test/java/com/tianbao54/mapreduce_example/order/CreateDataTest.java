package com.tianbao54.mapreduce_example.order;

import org.junit.Test;

public class CreateDataTest {

	String[] accounts = { "tianbao54@163.com", "yuppychang@foxmail.com", "tianbao54@gmail.com","yuppychang@ibm.cn.com" };

	@Test
	public void testCreateData() {

		java.text.DecimalFormat format = new java.text.DecimalFormat("#.##");

		for (int i = 0; i < 100; i++) {
			String line = "";

			line = accounts[(int) (Math.random() * 4)] + "\t" + format.format(Math.random() * 100) + "\t" + format.format(Math.random() * 50);

			System.out.println(line);
		}
	}
}

package com.tianbao54.io.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.tianbao54.io.listener.KeyListener;

public class Client {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter out;

	private String getUserInfo(BufferedReader info) throws IOException {
		return info.readLine();
	}

	private void init() {

		BufferedReader info = null;
		try {
			socket = new Socket("localhost", 9999);

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());

			System.out.print("请输入用户名：");
			info = new BufferedReader(new InputStreamReader(System.in));
			out.println(getUserInfo(info));
			out.flush();

			System.out.println("当前在线用户：[" + reader.readLine() + "]");

			System.out.print("请输入需要连接的用户：");
			String connectUser = getUserInfo(info);
			out.println(connectUser);
			out.flush();

			System.out.println(reader.readLine());
			String needConform = reader.readLine();
			System.out.println(needConform);

			
			if (needConform.indexOf("y/n") != -1) {
				String conform = info.readLine();
				out.println(conform);
				out.flush();

				if (conform.equals("y")) {
					connectUser = "server";
				} else if (conform.equals("n")) {
					return;
				} else {
					System.out.println("输入错误");
					return;
				}
			}

			new Thread(new KeyListener(out)).start();
			System.out.println("key listener has been started.");
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				if (line.indexOf("已经建立连接！") != -1) {
					int start = line.indexOf("[");
					int end = line.indexOf("]");
					connectUser = line.substring(start + 1, end);
				}
				
				System.out.println("from " + connectUser + ":" + line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				info.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Client().init();
	}
}
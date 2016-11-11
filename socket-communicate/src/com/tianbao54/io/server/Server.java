package com.tianbao54.io.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private static Map<String, Socket> online_users = new ConcurrentHashMap<String, Socket>();
	private static List<String> wait_list = new ArrayList<String>();

	private void init() {

		ExecutorService threadPool = Executors.newCachedThreadPool();
		ServerSocket server = null;
		try {
			server = new ServerSocket(9999);
			System.out.println("server is starting now.");
			while (true) {

				Socket socket = server.accept();
				System.out.println("get a new connect.");
				threadPool.execute(new ServerHandler(socket));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new Server().init();
	}

	private class ServerHandler implements Runnable {

		private Socket socket;
		private BufferedReader reader = null;
		private PrintWriter out = null;
		private Socket targetSocket;
		String username = null;

		ServerHandler(Socket socket) {
			this.socket = socket;
			init();
		}

		private void init() {
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private String getOnlineUsers() {

			StringBuilder onlineUsers = new StringBuilder();
			for (Map.Entry<String, Socket> entry : online_users.entrySet()) {
				onlineUsers.append(entry.getKey()).append(",");
			}

			return onlineUsers.toString();
		}

		private boolean connect() {

			try {
				String to = reader.readLine();

				out.println("正在与[" + to + "] 获取连接...");
				out.flush();

				if (null != Server.online_users.get(to)) {
					out.println("已经连接上[" + to + "]");
					out.flush();
					wait_list.remove(to);
					
					this.out.println("您已经与[" + to + "]用户连接！");
					this.out.flush();
					targetSocket = Server.online_users.get(to);
					
					PrintWriter out = new PrintWriter(targetSocket.getOutputStream());
					out.println("新用户[" + username + "]已经与您连接上");
					out.flush();
					return true;
				} else {
					out.println("用户[" + to + "]不存在，是否等待建立连接？[y/n]");
					out.flush();

					if (reader.readLine().equals("y")) {
						wait_list.add(username);
						while (wait_list.contains(username)) {
							
							out.println("等待连接中....");
							out.flush();
							Thread.sleep(3000);
						}
						
						out.println("与  [" + to + "] 已经建立连接！");
						out.flush();
						
						targetSocket = Server.online_users.get(to);
						return true;
					}

					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public void run() {
			
			PrintWriter targetOut = null;
			try {
				String onlineUsers = getOnlineUsers();
				username = reader.readLine();
				// 第一步，先记把当前用户登陆记下。
				Server.online_users.put(username, socket);
				// 第二步，把当前在线的用户传给 client.
				out.println(onlineUsers);
				out.flush();

				boolean isConnected = connect();

				if (isConnected) {
					
					targetOut = new PrintWriter(this.targetSocket.getOutputStream());
					
					//new Thread(new KeyListener(targetOut)).start();
					String line = null;
					while ((line = reader.readLine()) != null) {
						
						targetOut.println(line);
						targetOut.flush();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (null != username) {
						Server.online_users.remove(username);
					}
					socket.close();
					out.close();
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
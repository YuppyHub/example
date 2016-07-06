package com.tianbao54.hdfs.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class RPCClient {
	
	public static void main(String[] args) throws IOException {
		
		Bizable bizable = RPC.getProxy(Bizable.class, 1L, new InetSocketAddress("9.181.61.129",9999), new Configuration());
		String result = bizable.sayHi("zhangsan ");
		System.out.println(result);
		
		RPC.stopProxy(bizable);
	}
}
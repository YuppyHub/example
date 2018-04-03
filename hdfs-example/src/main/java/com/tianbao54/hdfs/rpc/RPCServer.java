package com.tianbao54.hdfs.rpc;

import java.io.IOException;
import java.io.Serializable;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;

public class RPCServer implements Bizable,Serializable{

	private static final long serialVersionUID = 1L;
	
	public String sayHi (String name) {
		
		return "hi ," + name;
	}
	
	public static void main(String[] args) throws HadoopIllegalArgumentException, IOException {
		
		Configuration config = new Configuration();
		Builder builder = new RPC.Builder(config);
		Server server = builder.setProtocol(Bizable.class).setInstance(new RPCServer()).setBindAddress("9.181.61.129").setPort(9999).build();
		server.start();
	}
}
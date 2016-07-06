package com.ibm.crl.elasticsearch.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class Client {

	private static TransportClient client;
	static {
		try {
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("yuppy01"), 9300));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static void get () {
		GetResponse response = client.prepareGet("megacorp", "employee", "1").get();
		System.out.println(response.getIndex());
		System.out.println(response.getId());
		System.out.println(response.getSourceAsString());
	}
	
	public static void put () {
		
	}

	
	public static void main(String[] args) {
		get();
	}
}

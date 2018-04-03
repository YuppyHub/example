package com.tianbao54.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

public class HBaseOpenatorTest {

	private Admin admin;
	private Configuration config = null;
	private Connection connection = null;

	@Before
	public void init() throws IOException {
		config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "yuppy01:2181");

		connection = ConnectionFactory.createConnection(config);
		admin = connection.getAdmin();
	}
	
	@Test
	public void creatTable () throws IOException {
		
		HTableDescriptor htableDesc = new HTableDescriptor(TableName.valueOf("h_user2"));
		
		HColumnDescriptor info = new HColumnDescriptor("info");
		info.setMaxVersions(3);
		HColumnDescriptor data = new HColumnDescriptor("data");
		
		htableDesc.addFamily(info);
		htableDesc.addFamily(data);
		
		admin.createTable(htableDesc);
	}
	
	@Test
	public void dropTable () throws IOException {
		admin.disableTable(TableName.valueOf("h_user2"));
		admin.deleteTable(TableName.valueOf("h_user2"));
	}

	@Test
	public void testPut() throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_user"));

		Put put = new Put("rowkey0001".getBytes());
		put.addColumn("info".getBytes(), "name".getBytes(), "zhangsan".getBytes());
		put.addColumn("info".getBytes(), "age".getBytes(), "100".getBytes());
		put.addColumn("data".getBytes(), "money".getBytes(), "100000000".getBytes());

		table.put(put);
		table.close();
	}

	@Test
	public void testPerformance() throws IOException {
		
		Table table = connection.getTable(TableName.valueOf("h_user"));

		List<Put> puts = new ArrayList<Put>(10000);
		long start = System.currentTimeMillis();
		
		for (int i = 0 ; i < 1000000 ; i ++) {
			Put put = new Put(("rowkey0000" + i).getBytes());
			put.addColumn("info".getBytes(), "name".getBytes(), ("zhangsan" + i).getBytes());
			put.addColumn("info".getBytes(), "age".getBytes(), "100".getBytes());
			put.addColumn("data".getBytes(), "money".getBytes(), "100000000".getBytes());
			puts.add(put);
			if (i % 10000 == 0) {
				table.put(puts);
				puts = new ArrayList<Put>();
			}
		}
		
		table.put(puts);
		table.close();
		
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000 );
	}
	
	@Test
	public void testGet () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_user"));
		
		Get get = new Get("rowkey0000110504".getBytes());
		
		Result result = table.get(get);
		String money = Bytes.toString(result.getValue("info".getBytes(), "money".getBytes()));
		System.out.println(money);
	}
	
	@Test
	public void testScan () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_user"));
		
		Scan scan = new Scan ();
		scan.setStartRow("rowkey00001".getBytes());
		scan.setStopRow("rowkey000010".getBytes());
		
		ResultScanner rs = table.getScanner(scan);
		for (Result result : rs) {
			String name = Bytes.toString(result.getValue("info".getBytes(), "name".getBytes()));
			System.out.println(name);
		}
		
		table.close();
	}
	
	@Test
	public void testDelete () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_user"));
		
		Delete delete = new Delete ("rowkey000010".getBytes());
		
		table.delete(delete);
		table.close();
	}
}

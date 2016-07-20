package com.tianbao54.hbase.filter.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FuzzyRowFilter;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.TimestampsFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;
import org.junit.Before;
import org.junit.Test;

public class HBaseFilterTest {

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
	public void testCreateTable() throws IOException {
		HTableDescriptor htableDesc = new HTableDescriptor(TableName.valueOf("h_filter"));

		HColumnDescriptor info = new HColumnDescriptor("info");
		info.setMaxVersions(3);
		HColumnDescriptor data = new HColumnDescriptor("data");

		htableDesc.addFamily(info);
		htableDesc.addFamily(data);
		admin.createTable(htableDesc);
		System.out.println("create table completed.");
	}
	
	@Test
	public void testDropTable () throws IOException {
		admin.disableTable(TableName.valueOf("h_filter"));
		admin.deleteTable(TableName.valueOf("h_filter"));
	}
	
	@Test
	public void testPutData () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		
		Put put = null;
		List<Put> puts = new ArrayList<Put>();
		for (int i = 0 ; i < 10000 ; i ++) {
			put = new Put(Bytes.toBytes("rowkey" + i));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("age"), Bytes.toBytes(((int)(Math.random() * 100))));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("name[" + i + "]"));
			put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("class"), Bytes.toBytes(((int)(Math.random() * 10)) + "班"));
			put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("money"), Bytes.toBytes(Math.random() * 100000000));
			puts.add(put);
		}
		table.put(puts);
		table.close();
		System.out.println("put data into table completed.");
	}
	
	@Test
	public void testQueryAllData () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	/**
	 * @Title: testSingleColumnValueFilter   
	 * @Description: TODO(对列的值做匹配查询可以是  >  < = !=)   
	 * @param: @throws IOException      
	 * @return: void      
	 * @throws
	 */
	@Test
	public void testSingleColumnValueFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		FilterList filters = new FilterList();
		SingleColumnValueFilter sigleFilter = new SingleColumnValueFilter(Bytes.toBytes("info"), Bytes.toBytes("age"),CompareOp.EQUAL,Bytes.toBytes(3));
		filters.addFilter(sigleFilter);
		
		scan.setFilter(filters);
		
		ResultScanner result = table.getScanner(scan);
		output(result);
	}
	
	/**
	 * @Title: testMultipleColumnPrefixFilter   
	 * @Description: TODO(查询指定列，按列的前缀查询)   
	 * @param: @throws IOException      
	 * @return: void      
	 * @throws
	 */
	@Test
	public void testMultipleColumnPrefixFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		FilterList filters = new FilterList();
		byte[][] prefixes = new byte[][] {Bytes.toBytes("ag"), Bytes.toBytes("na"),Bytes.toBytes("cl")};
		MultipleColumnPrefixFilter multipleFilter = new MultipleColumnPrefixFilter(prefixes);
		filters.addFilter(multipleFilter);
		
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	/**
	 * @Title: testColumnPrefixFilter   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @throws IOException      
	 * @return: void      
	 * @throws
	 */
	@Test
	public void testColumnPrefixFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		FilterList filters = new FilterList();
		ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(Bytes.toBytes("ag"));
		filters.addFilter(columnPrefixFilter);
		
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	/**
	 * @Title: testPrefixFilter   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @throws IOException      
	 * @return: void      
	 * @throws
	 */
	@Test
	public void testPrefixFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		FilterList filters = new FilterList();
		PrefixFilter prefixFilter = new PrefixFilter(Bytes.toBytes("rowkey999"));
		
		filters.addFilter(prefixFilter);
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	@Test
	public void testQualifierFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		FilterList filters = new FilterList();
		QualifierFilter qualifierFilter = new QualifierFilter(CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("name"))); 
		
		filters.addFilter(qualifierFilter);
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	/**
	 * @Title: testFuzzyRowFilter   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @throws IOException      
	 * @return: void       ?
	 * @throws
	 */
	@Test
	public void testFuzzyRowFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		List<Pair<byte[], byte[]>> fuzzyKeysData = new ArrayList<Pair<byte[], byte[]>>();
		Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>();
		pair.setFirst(Bytes.toBytesBinary("rowkey88?"));
		pair.setSecond(new byte[] {0,0,0,0,0,0,0,0,1});
		fuzzyKeysData.add(pair);
		
		FilterList filters = new FilterList();
		FuzzyRowFilter fuzzyFilter = new FuzzyRowFilter(fuzzyKeysData);
		
		filters.addFilter(fuzzyFilter);
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	@Test
	public void testTimestampsFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		List<Long> times = new ArrayList<Long>();
		times.add(1468916113225L);
		
		FilterList filters = new FilterList();
		TimestampsFilter timeFilter = new TimestampsFilter(times);
		
		filters.addFilter(timeFilter);
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	@Test
	public void testRegexStringComparator () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		FilterList filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        RegexStringComparator regexString = new RegexStringComparator("1.");
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("info"),Bytes.toBytes("class"),CompareOp.EQUAL,regexString);
        
        filters.addFilter(filter);
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	@Test
	public void testSubstringComparator () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		FilterList filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		SubstringComparator comp = new SubstringComparator("5]");   // looking for 'my value'
		SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("info"),Bytes.toBytes("name"),CompareOp.EQUAL,comp);
		
		filters.addFilter(filter);
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	@Test
	public void testColumnRangeFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		byte[] startColumn = Bytes.toBytes("a");
        byte[] endColumn = Bytes.toBytes("m");
        
        FilterList filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        ColumnRangeFilter rangeFilter = new ColumnRangeFilter(startColumn, true, endColumn, true);
        
        filters.addFilter(rangeFilter);
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	@Test
	public void testPageFilter () throws IOException {
		Table table = connection.getTable(TableName.valueOf("h_filter"));
		Scan scan = new Scan ();
		
		FilterList filters = new FilterList(FilterList.Operator.MUST_PASS_ALL);
		PageFilter pageFilter = new PageFilter(100);
		filters.addFilter(pageFilter);
		scan.setFilter(filters);
		ResultScanner result = table.getScanner(scan);
		
		output(result);
	}
	
	private void output (ResultScanner result) {
		for (Iterator<Result> iter = result.iterator() ; iter.hasNext();) {
			Result rs = iter.next();
			int age = Bytes.toInt(null  == rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("age")) ? Bytes.toBytes(0) : rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("age")));
			String name = Bytes.toString(null == rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")) ? Bytes.toBytes("NULL") : rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")));
			String clazz = Bytes.toString(null == rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("class")) ? Bytes.toBytes("NULL") : rs.getValue(Bytes.toBytes("info"), Bytes.toBytes("class")));
			double money = Bytes.toDouble(null == rs.getValue(Bytes.toBytes("data"), Bytes.toBytes("money")) ? Bytes.toBytes(0.0) : rs.getValue(Bytes.toBytes("data"), Bytes.toBytes("money")));
			System.out.println("name : " + name + "   age : " + age + "  class : " + clazz + "  money : " + money);
		}
	}
}
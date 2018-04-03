package com.ibm.crl.elastichsearch.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibm.crl.elasticsearch.client.Conference;
import com.ibm.crl.elasticsearch.client.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = com.ibm.crl.elasticsearch.client.ApplicationConfiguration.class)	
public class ElasticsearchOperationsTest {

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired 
	ElasticsearchOperations operations;
	
	
	@Test
	public void testGet () {
		TransportClient client = null;
		try {
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("yuppy01"), 9300));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ElasticsearchTemplate template = new ElasticsearchTemplate(client);
		CriteriaQuery query = new CriteriaQuery(new Criteria());
		
		List<Employee> lists = template.queryForList(query, Employee.class);
		for (Employee employee : lists) {
			System.out.println(employee.getFirst_name() + "." + employee.getLast_name());
		}
		
		System.out.println(lists);
	}
	
	@Test
	public void textSearch() throws ParseException {

		String expectedDate = "2014-10-29";
		String expectedWord = "java";
		CriteriaQuery query = new CriteriaQuery(
				new Criteria("_all").contains(expectedWord).and(new Criteria("date").greaterThanEqual(expectedDate)));

		List<Conference> result = operations.queryForList(query, Conference.class);

		for (Conference conference : result) {
			
		}
	}

	@Test
	public void geoSpatialSearch() {

		GeoPoint startLocation = new GeoPoint(50.0646501D, 19.9449799D);
		String range = "330mi"; // or 530km
		CriteriaQuery query = new CriteriaQuery(new Criteria("location").within(startLocation, range));

		List<Conference> result = operations.queryForList(query, Conference.class);

	}
}

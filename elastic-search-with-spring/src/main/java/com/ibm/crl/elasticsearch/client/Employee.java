package com.ibm.crl.elasticsearch.client;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(indexName = "megacorp", type = "employee", shards = 1, replicas = 0,
		refreshInterval = "-1")
public class Employee {

	
	private @Id String id;
	private String first_name;
	private String last_name;
	private int age;
	private String about;
	private List<String>interests;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public List<String> getInterests() {
		return interests;
	}
	public void setInterests(List<String> interests) {
		this.interests = interests;
	}
}
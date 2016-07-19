package com.tianbao54.mapreduce.example.sort.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * @ClassName:  UserBean   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 张宇鹏  
 * @date:   2016年7月18日 下午3:50:32   
 */
public class UserBean implements WritableComparable<UserBean>{

	private int id;
	private String name;
	private String nation;
	private int age;
	
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(id);
		out.writeUTF(name);
		out.writeUTF(nation);
		out.writeInt(age);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		id = in.readInt();
		name = in.readUTF();
		nation = in.readUTF();
		age = in.readInt();
	}

	/**
	 * 如果不重写 compare reducer 只会写出一条数据
	 * <p>Title: compareTo</p>   
	 * <p>Description: </p>   
	 * @param o
	 * @return   
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(UserBean o) {
		
		int compareName = name.compareTo(o.getName());
		
		if (compareName == 0) {
			return age > o.getAge() ? 1 : -1;
		}
		
		return compareName;
	}
	
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", name=" + name + ", nation=" + nation + ", age=" + age + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
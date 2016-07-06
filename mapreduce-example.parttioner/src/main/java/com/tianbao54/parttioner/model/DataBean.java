package com.tianbao54.parttioner.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class DataBean implements Writable{

	private String telNo;
	private long upPayload;
	private long downPayload;
	private long totalPayload;
	
	public DataBean(String telNo, long upPayload, long downPayload) {
		super();
		this.telNo = telNo;
		this.upPayload = upPayload;
		this.downPayload = downPayload;
		this.totalPayload = upPayload + downPayload;
	}

	public DataBean() {
		super();
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public long getUpPayload() {
		return upPayload;
	}

	public void setUpPayload(long upPayload) {
		this.upPayload = upPayload;
	}

	public long getDownPayload() {
		return downPayload;
	}

	public void setDownPayload(long downPayload) {
		this.downPayload = downPayload;
	}

	public long getTotalPayload() {
		return totalPayload;
	}

	public void setTotalPayload(long totalPayload) {
		this.totalPayload = totalPayload;
	}

	// Serialize
	/**
	 * notice: 
	 * 	1:orders
	 *  2:type
	 */
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.telNo);
		out.writeLong(this.upPayload);
		out.writeLong(this.downPayload);
		out.writeLong(this.totalPayload);
	}

	// DeSerialize
	@Override
	public void readFields(DataInput in) throws IOException {
		this.telNo = in.readUTF();
		this.upPayload = in.readLong();
		this.downPayload = in.readLong();
		this.totalPayload = in.readLong();
	}

	@Override
	public String toString() {
		return "DataBean [telNo=" + telNo + ", upPayload=" + upPayload + ", downPayload=" + downPayload
				+ ", totalPayload=" + totalPayload + "]";
	}
}
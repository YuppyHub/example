package com.tianbao54.mapreduce.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class UserInfo implements WritableComparable<UserInfo> {
	

	private String account;
	private double income;
	private double expense;
	private double surplus;
	
	
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInfo(String account, double income, double expense) {
		super();
		this.account = account;
		this.income = income;
		this.expense = expense;
		this.surplus = income - expense;
	}
	
	public void set(String account, double income, double expense) {
		this.account = account;
		this.income = income;
		this.expense = expense;
		this.surplus = income - expense;
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(account);
		out.writeDouble(income);
		out.writeDouble(expense);
		out.writeDouble(surplus);
	}

	public void readFields(DataInput in) throws IOException {
		this.account = in.readUTF();
		this.income = in.readDouble();
		this.expense = in.readDouble();
		this.surplus = in.readDouble();
	}

	public int compareTo(UserInfo o) {
		
		if (this.income == o.getIncome()) {
			return this.expense > o.getExpense() ? -1 : 1;
		} else {
			return this.income > o.getIncome() ? 1 : -1;
		}
	}
	
	@Override
	public String toString() {
		return account + "\t" + income + "\t" + expense + "\t" + surplus;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public double getSurplus() {
		return surplus;
	}

	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}
}
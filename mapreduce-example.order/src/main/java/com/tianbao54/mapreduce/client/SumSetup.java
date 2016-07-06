package com.tianbao54.mapreduce.client;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.tianbao54.mapreduce.model.UserInfo;

public class SumSetup {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration config = new Configuration();
		Job job = Job.getInstance(config,"sum for sale");
		
		job.setJarByClass(SumSetup.class);
		job.setMapperClass(SumMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(UserInfo.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(SumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(UserInfo.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	public static class SumMapper extends Mapper <LongWritable, Text, Text, UserInfo> {

		private Text key = new Text();
		private UserInfo value = new UserInfo();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, UserInfo>.Context context) throws IOException, InterruptedException {
			
			String line = value.toString();
			String [] fields = line.split("\t");
			String account = fields [0];
			Double income = Double.parseDouble(fields[1]);
			Double expense = Double.parseDouble(fields[2]);
			
			this.key.set(account);
			this.value.set(account, income, expense);
			
			context.write(this.key, this.value);
		}
	}
	
	public static class SumReducer extends Reducer<Text, UserInfo, Text, UserInfo> {

		private UserInfo value = new UserInfo ();
		@Override
		protected void reduce(Text key, Iterable<UserInfo> values, Reducer<Text, UserInfo, Text, UserInfo>.Context context)
				throws IOException, InterruptedException {
			
			double inSum = 0;
			double expenseSum = 0;
			
			for (UserInfo userInfo : values) {
				inSum += userInfo.getIncome();
				expenseSum += userInfo.getExpense();
			}
			this.value.set("", inSum, expenseSum);
			context.write(key, this.value);
		}
	}
}

package com.tianbao54.mapreduce.client;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.tianbao54.mapreduce.model.UserInfo;

public class SortSetup {

	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {

		Configuration config = new Configuration();
		Job job = Job.getInstance(config,"sum for sale");
		
		job.setJarByClass(SortSetup.class);
		job.setMapperClass(SortMapper.class);
		job.setMapOutputKeyClass(UserInfo.class);
		job.setMapOutputValueClass(NullWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(SortReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(UserInfo.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	public static class SortMapper extends Mapper<LongWritable, Text, UserInfo, NullWritable> {

		private UserInfo key = new UserInfo ();
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, UserInfo, NullWritable>.Context context)throws IOException, InterruptedException {
			
			try {
				String line = value.toString();
				String [] fields = line.split("\t");
				String account = fields [0];
				Double income = Double.parseDouble(fields[1]);
				Double expense = Double.parseDouble(fields[2]);
				this.key.set(account, income, expense);
				context.write(this.key, NullWritable.get());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static class SortReducer extends Reducer<UserInfo, NullWritable, Text, UserInfo>{

		private Text key = new Text ();
		@Override
		protected void reduce(UserInfo key, Iterable<NullWritable> value,Reducer<UserInfo, NullWritable, Text, UserInfo>.Context context) throws IOException, InterruptedException {
			this.key.set(key.getAccount());
			context.write(this.key, key);
		}
	}
}
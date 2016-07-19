package com.tianbao54.mapreduce.example.sort;

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

import com.tianbao54.mapreduce.example.sort.model.UserBean;

public class Sort {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration config = new Configuration();
		Job job = Job.getInstance(config,"sort ");
		
		job.setJarByClass(Sort.class);
		
		job.setMapperClass(SortMapper.class);
		job.setMapOutputKeyClass(UserBean.class);
		job.setMapOutputValueClass(NullWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(SortReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(UserBean.class);
		
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	public static class SortMapper extends Mapper<LongWritable, Text, UserBean, NullWritable> {

		private UserBean user = new UserBean();
		
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, UserBean, NullWritable>.Context context)
				throws IOException, InterruptedException {
			
			String line = value.toString();
			String values [] = line.split("\t");
			user.setId(Integer.parseInt(values[0]));
			user.setName(values[1]);
			user.setNation(values[2]);
			user.setAge(Integer.parseInt(values[3]));
			NullWritable nullWritable = NullWritable.get();
			context.write(user, nullWritable);
		}
	}
	
	public static class SortReducer extends Reducer<UserBean, NullWritable, Text, UserBean> {

		private Text key = new Text();
		@Override
		protected void reduce(UserBean key, Iterable<NullWritable> values,
				Reducer<UserBean, NullWritable, Text, UserBean>.Context context) throws IOException, InterruptedException {
			
			this.key.set(key.getName());
			context.write(this.key, key);
		}
	}
}
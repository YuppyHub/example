package com.tianbao54.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @ClassName:  WordCountExample   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 张宇鹏  
 * @date:   2016年6月28日 下午5:35:09   
 */
public class WordCountExample {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

		// build job object.
		Job job = Job.getInstance(new Configuration(),"word count example");
		
		// set by main function
		job.setJarByClass(WordCountExample.class);
		
		// set mapper attribute
		job.setMapperClass(WordCountMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		FileInputFormat.setInputPaths(job, new Path("/words.txt"));
		
		// set reduce attribute
		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		FileOutputFormat.setOutputPath(job, new Path("/wordcountResult"));
		
		// print detail of process & commit job
		job.waitForCompletion(true);
	}
}
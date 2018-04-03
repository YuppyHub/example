package com.tianbao54.mapreduce.example.wordcount;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @ClassName:  WordCount   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 张宇鹏  
 * @date:   2016年7月18日 下午1:15:16   
 */
public class WordCount {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration config = new Configuration();
		Job job = Job.getInstance(config," word count ");
		
		job.setJarByClass(WordCount.class);
		
		job.setMapperClass(WordCountMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	public static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

		private final IntWritable one = new IntWritable(1);
		private Text word = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer token = new StringTokenizer(line, "\t");
			
			while (token.hasMoreTokens()) {
				word.set(token.nextToken());
				context.write(word, one);
			}
		}
	}
	
	public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			
			int sum = 0;
			
			for (Iterator<IntWritable> iter = values.iterator() ; iter.hasNext();) {
				IntWritable count = iter.next();
				sum += count.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
}
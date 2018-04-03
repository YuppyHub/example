package com.tianbao54.mapreduce.example.combiner.wordcount;

import java.io.IOException;
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

public class WordCount {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration config = new Configuration();
		Job job = Job.getInstance(config,"word count with combiner");
		
		job.setJarByClass(WordCount.class);
		
		job.setMapperClass(WordCountMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(WordCountReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setCombinerClass(WordCountCombiner.class);
		
		job.waitForCompletion(true);
	}
	
	/**
	 * combiner 是一种特殊的 combiner，在 map 端执行，减少数据传输
	 * @ClassName:  WordCountCombiner   
	 * @Description:TODO(这里用一句话描述这个类的作用)   
	 * @author: 张宇鹏  
	 * @date:   2016年7月18日 下午5:06:29   
	 *
	 */
	public static class WordCountCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

		private IntWritable value = new IntWritable();
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			
			int count = 0;
			for (IntWritable intWritable : values) {
				count += intWritable.get();
			}
			value.set(count);
			context.write(key, value);
		}
	}
	
	public static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

		private IntWritable intWritable = new IntWritable(1);
		private Text key = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			
			StringTokenizer token = new StringTokenizer(value.toString(), "\t");
			while (token.hasMoreTokens()) {
				this.key.set(token.nextToken());
				context.write(this.key, intWritable);
			}
		}
	}
	
	public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		private IntWritable value = new IntWritable();
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			int count = 0;
			for (IntWritable intWritable : values) {
				count += intWritable.get();
			}
			value.set(count);
			context.write(key, value);
		}
	}
}
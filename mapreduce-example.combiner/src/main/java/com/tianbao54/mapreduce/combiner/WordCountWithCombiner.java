package com.tianbao54.mapreduce.combiner;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @ClassName:  WordCountWithCombiner   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 张宇鹏  
 * @date:   2016年7月1日 下午1:51:29   
 */
public class WordCountWithCombiner {

	public static void main(String[] args) throws IOException {
		Configuration config = new Configuration ();
		Job job = Job.getInstance(config,"WordCount With Combiner.");
		job.setJarByClass(WordCountWithCombiner.class);
		
		job.setMapperClass(CombinerMapper.class);
	}
	
	public class CombinerMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context)
				throws IOException, InterruptedException {
			
		}
	}
	
	public class CombinerReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

		@Override
		protected void reduce(Text key, Iterable<LongWritable> values,Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
		}
	}
}

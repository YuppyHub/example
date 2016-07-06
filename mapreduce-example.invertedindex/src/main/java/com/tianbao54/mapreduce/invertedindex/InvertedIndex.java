package com.tianbao54.mapreduce.invertedindex;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class InvertedIndex {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration config = new Configuration ();
		Job job = Job.getInstance(config,"invertedIndex");
		job.setJarByClass(InvertedIndex.class);
		
		job.setMapperClass(InvertedIndexMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setCombinerClass(InveredIndexCombiner.class);
		
		job.setReducerClass(InvertedIndexReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	public static class InvertedIndexMapper extends Mapper<LongWritable, Text, Text, Text> {

		private Text key = new Text();
		private Text value = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
			String line = value.toString();
			String [] fields = line.split(" ");
			FileSplit split = (FileSplit) context.getInputSplit();
			String name = split.getPath().getName();
			
			for (String word : fields) {
				this.key.set(word + "->" + name);
				this.value.set(String.valueOf(1));
				context.write(this.key, this.value);
			}
		}
	}
	
	public static class InveredIndexCombiner extends Reducer <Text, Text, Text, Text> {

		private Text key = new Text();
		private Text value = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
			
			String [] wordAndPath = key.toString().split("->");
			int count = 0;
			for (Text text : values) {
				count += Integer.parseInt(text.toString());
			}
			this.key.set(wordAndPath[0]);
			this.value.set(wordAndPath[1] + "->" + count);
			context.write(this.key, this.value);
		}
	}
	
	public static class InvertedIndexReducer extends Reducer<Text, Text, Text, Text> {

		private Text key = new Text();
		private Text value = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
			
			String result = "";
			for (Text text : values) {
				result += (text.toString() + "  ");
			}
			this.key.set(key);
			this.value.set(result);
			context.write(this.key, this.value);
		}
	}
}
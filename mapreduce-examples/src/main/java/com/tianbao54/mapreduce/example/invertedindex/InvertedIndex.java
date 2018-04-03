package com.tianbao54.mapreduce.example.invertedindex;

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

/**
 * @ClassName:  InvertedIndex   
 * @Description:TODO(倒排索引)   
 * @author: 张宇鹏  
 * @date:   2016年7月19日 上午11:31:56   
 */
public class InvertedIndex {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration config = new Configuration();
		Job job = Job.getInstance(config,"Inverted Index.");
		
		job.setJarByClass(InvertedIndex.class);
		
		job.setMapperClass(InvertedIndexMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(InvertedIndexReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setCombinerClass(InvertedIndexCombiner.class);
		job.waitForCompletion(true);
	}
	
	/**
	 * @ClassName:  InvertedIndexMapper   
	 * @Description:TODO(通过 Context 获取到文件名，记录每个单词在每个文件出现的次数)   
	 * @author: 张宇鹏  
	 * @date:   2016年7月19日 上午11:32:14   
	 */
	public static class InvertedIndexMapper extends Mapper<LongWritable, Text, Text, Text> {

		
		private Text key = new Text();
		private Text value = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			String line = value.toString();
			String values [] = line.split("\t");
			FileSplit split = (FileSplit) context.getInputSplit();
			String name = split.getPath().getName();
			
			for (String word : values) {
				this.key.set(word + "->" + name);
				this.value.set(String.valueOf(1));
				context.write(this.key, this.value);
			}
		}
	}
	
	/**
	 * @ClassName:  InvertedIndexCombiner   
	 * @Description:TODO(接收来自  mapper 的参数，以 word 为 key , 以文件名和当前文件出现 word 次数作为 value 写入给 reducer.)   
	 * @author: 张宇鹏  
	 * @date:   2016年7月19日 上午11:33:11   
	 */
	public static class InvertedIndexCombiner extends Reducer<Text, Text, Text, Text> {

		private Text key = new Text();
		private Text value = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			String [] wordAndPath = key.toString().split("->");
			int count = 0;
			for (Text num : values) {
				count += Integer.parseInt(num.toString());
			}
			this.key.set(wordAndPath[0]);
			this.value.set(wordAndPath[1] + "->" + count);
			context.write(this.key, this.value);
		}
	}
	
	/**
	 * @ClassName:  InvertedIndexReducer   
	 * @Description:TODO(接收来自 Combiner 的参数，把相同  key 的 values(每个文件出现word 的文件名和出现的次数) 相加，然后写入到 hdfs 文件系统上.)   
	 * @author: 张宇鹏  
	 * @date:   2016年7月19日 上午11:36:14   
	 */
	public static class InvertedIndexReducer extends Reducer<Text, Text, Text, Text> {

		private Text value = new Text();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			String result = "";
			for (Text value : values) {
				result += (value + "  ");
			}
			this.value.set(result);
			context.write(key, this.value);
		}
	}
}
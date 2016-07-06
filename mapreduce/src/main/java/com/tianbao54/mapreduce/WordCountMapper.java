package com.tianbao54.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, LongWritable>.Context context) throws IOException, InterruptedException {
		// receive data
		String line = value.toString();
		// split data
		String [] words = line.split(" ");
		// foreach
		
		for (String word : words) {
			// mark of the word .
			context.write(new Text(word), new LongWritable(1));
		}
	}
}
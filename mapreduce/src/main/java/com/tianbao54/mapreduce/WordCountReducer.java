package com.tianbao54.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @ClassName: WordCountReducer
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 张宇鹏
 * @date: 2016年6月28日 下午5:09:06
 */
public class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	@Override
	protected void reduce(Text key, Iterable<LongWritable> value2s,Reducer<Text, LongWritable, Text, LongWritable>.Context context) throws IOException, InterruptedException {
		
		//defined number count 
		long count = 0;
		// loops
		for (LongWritable longWritable : value2s) {
			count += longWritable.get();
		}
		// output.
		context.write(key, new LongWritable(count));
	}
}
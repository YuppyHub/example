package com.tianbao54.mapreduce.datasum;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.tianbao54.mapreduce.model.DataBean;

/**
 * @ClassName:  DataSum   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 张宇鹏  
 * @date:   2016年6月29日 下午4:02:33   
 */
public class DataSum {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"data sum");
		
		job.setJarByClass(DataSum.class);
		job.setMapperClass(DataSumMapper.class);
		// when key2 equals key3 and value2 equals value3  this step can ignor
//		job.setMapOutputKeyClass(Text.class);
//		job.setMapOutputValueClass(DataBean.class);
		FileInputFormat.setInputPaths(job, new Path("/phone.txt"));
		
		job.setReducerClass(DataSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DataBean.class);
		FileOutputFormat.setOutputPath(job, new Path("/sumResult"));
		
		job.waitForCompletion(true);
	}
	
	public static class DataSumMapper extends Mapper<LongWritable, Text, Text, DataBean> {

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, DataBean>.Context context) throws IOException, InterruptedException {
			
			String line = value.toString();
			String [] fields = line.split(",");
			
			try {
				
				String telNo = fields[0];
				long upPayload = Long.parseLong(fields[1]);
				long downPayload = Long.parseLong(fields[2]);
				DataBean db = new DataBean(telNo,upPayload,downPayload);
				context.write(new Text(telNo), db);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class DataSumReducer extends Reducer<Text, DataBean, Text, DataBean> {

		@Override
		protected void reduce(Text key, Iterable<DataBean> value2s, Reducer<Text, DataBean, Text, DataBean>.Context context)
				throws IOException, InterruptedException {
			
			long upSum = 0;
			long downSum = 0;
			
			for (DataBean dataBean : value2s) {
				upSum += dataBean.getUpPayload();
				downSum += dataBean.getDownPayload();
			}
			DataBean bean = new DataBean ("",upSum,downSum);
			context.write(key, bean);
		}
	}
}
package com.tianbao54.parttioner.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.tianbao54.parttioner.model.DataBean;

/**
 * @ClassName:  DataSum   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 张宇鹏  
 * @date:   2016年6月29日 下午4:02:33   
 */
public class DataSum {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf,"data sum partitioner");
		
		job.setJarByClass(DataSum.class);
		job.setMapperClass(DataSumMapper.class);
		// when key2 equals key3 and value2 equals value3  this step can ignore
//		job.setMapOutputKeyClass(Text.class);
//		job.setMapOutputValueClass(DataBean.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(DataSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DataBean.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setNumReduceTasks(Integer.parseInt(args[2]));
		job.setPartitionerClass(ProvidePartitioner.class);
		
		job.waitForCompletion(true);
	}
	
	// after map , before reducer.
	public static class ProvidePartitioner extends Partitioner<Text, DataBean> {

		private static Map<String,Integer> provider = new HashMap<String,Integer>();
		static {
			provider.put("135", 1);
			provider.put("136", 1);
			provider.put("137", 1);
			provider.put("138", 1);
			provider.put("139", 1);
			
			provider.put("150", 2);
			provider.put("159", 2);
			
			provider.put("182", 3);
			provider.put("183", 3);
		}
		
		// return 分区号
		@Override
		public int getPartition(Text key, DataBean value, int numPartitions) {
			
			String account = key.toString();
			String subAcc = account.substring(0,3);
			
			Integer code = provider.get(subAcc);
			if (code == null) {
				code = 0;
			}
			
			return code;
		}
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
package com.tianbao54.mapreduce.example.partitioner.sum;

import java.io.IOException;

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

import com.tianbao54.mapreduce.example.sort.model.UserBean;

/**
 * @ClassName: SumWithPartitioner
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 张宇鹏
 * @date: 2016年7月18日 下午4:46:52
 */
public class SumWithPartitioner {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration config = new Configuration();
		Job job = Job.getInstance(config, "partitioner");

		job.setJarByClass(SumWithPartitioner.class);

		job.setMapperClass(SumMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(UserBean.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));

		job.setReducerClass(SumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(UserBean.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setPartitionerClass(NationPartitioner.class);

		job.setNumReduceTasks(Integer.parseInt(args[2]));
		job.waitForCompletion(true);
	}

	// after mapper after reducer
	public static class NationPartitioner extends Partitioner<Text, UserBean> {

		@Override
		public int getPartition(Text key, UserBean value, int numPartitions) {

			if (value.getNation().equals("USA")) {
				numPartitions = 0;
			} else if (value.getNation().equals("CHINA")) {
				numPartitions = 1;
			} else {
				numPartitions = 2;
			}
			return numPartitions;
		}
	}

	public static class SumMapper extends Mapper<LongWritable, Text, Text, UserBean> {

		private Text key = new Text();
		private UserBean value = new UserBean();

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, UserBean>.Context context)
				throws IOException, InterruptedException {

			String line = value.toString();
			String values[] = line.split("\t");
			this.value.setId(Integer.parseInt(values[0]));
			this.value.setName(values[1]);
			this.key.set(values[1]);
			this.value.setNation(values[2]);
			this.value.setAge(Integer.parseInt(values[3]));

			context.write(this.key, this.value);
		}
	}

	public static class SumReducer extends Reducer<Text, UserBean, Text, UserBean> {

		private UserBean user = new UserBean();

		@Override
		protected void reduce(Text key, Iterable<UserBean> values,
				Reducer<Text, UserBean, Text, UserBean>.Context context) throws IOException, InterruptedException {

			String name = "";
			int age = 0;
			for (UserBean user : values) {
				name = user.getName() + "\t";
				age += user.getAge();
				this.user.setNation(user.getNation());
			}
			user.setId(0);
			user.setAge(age);
			user.setName(name);
			context.write(key, this.user);
		}
	}
}
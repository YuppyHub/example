package com.tianbao54.hdfs.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @ClassName:  HDFSClient   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 张宇鹏  
 * @date:   2016年6月27日 下午4:30:56   
 */
public class HDFSClient {
	
	private FileSystem fs = null;
	
	@Before
	public void init () throws IOException, URISyntaxException, InterruptedException {
		Configuration conf = new Configuration();
		fs = FileSystem.get(new URI("hdfs://yuppy01:9000"), conf,"yuppy");
	}
	
	@Test
	public void testUpload () throws IllegalArgumentException, IOException {
		InputStream input = new FileInputStream (new File ("C:\\home\\iot4auser\\2016-06-06 16-26-21d9d289c6-f56a-41f9-97ef-52ee44c214e2"));
		OutputStream out = fs.create(new Path("/home/yuppy/test.txt"));
		IOUtils.copyBytes(input, out, 4096,true);
	}
	
	@Test
	public void testDownload () throws IllegalArgumentException, IOException {
		
		FSDataInputStream inputStream = fs.open(new Path("/home/yuppy/redis-3.2.1.tar.gz"));
		OutputStream out = new FileOutputStream(new File ("c:/home/redis.tar.gz"));
		IOUtils.copyBytes(inputStream, out, 4096,true);
	}
	
	@Test
	public void testDownload2 () throws IllegalArgumentException, IOException {
		
		fs.copyToLocalFile(new Path("/home/yuppy/redis-3.2.1.tar.gz"), new Path("/home/yuppy/redis-3.2.1.tar.gz"));
	}
	
	@Test
	public void testDelete () throws IllegalArgumentException, IOException {
		
		boolean result = fs.delete(new Path ("/home/yuppy/soft/name"), true);
		System.out.println(result);
	}
	
	@Test
	public void testmkidr () throws IllegalArgumentException, IOException {
		boolean result = fs.mkdirs(new Path("/home/yuppy/soft/name"));
		System.out.println(result);
	}
}
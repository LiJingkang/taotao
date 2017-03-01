package com.taotao.fastdfs;

import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Created by Skying on 2017/3/1.
 */
public class TestFastDFS {

	@Test
	public void uploadFile() throws Exception {
		// 1.向工程中添加jar包

		// 2.创建一个配置文件。配置tracker服务器地址

		// 3.加载配置文件
		ClientGlobal.init("E:/IDEA/taotao/taotao-manager-web/src/main/resources/resource/client.conf");
		// 4.创建一个TrackerClient对象。
		TrackerClient trackerClient = new TrackerClient();
		// 5.使用TrackerClint对象获得trackerserver对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 6.创建一个StorageServer的引用null就可以。
		StorageServer storageServer = null;
		// 7.创建一个StroageClient对象。trackerserver、StorageServer两个参数。
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 8.使用StorageClinet对象上传文件。
		// meta_list 元数据
		String[] strings = storageClient.upload_file("D:/1.jpe","jpe",null);
		for (String string : strings) {
			System.out.print(string);
		}
	}
}

package com.taotao.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Created by Skying on 2017/3/7.
 */
public class TestSolrCloud {

    @Test
    public void testSolrCloudAdDocument() throws Exception {
        // 1. 创建一个CloudSolrServer对象，构造方法中指定zookeeper的地址列表
        String ip = "192.168.56.2:2181,192.168.56.2:2182,192.168.56.2:2183";
        CloudSolrServer cloudSolrServer = new CloudSolrServer(ip);
        // 2. 需要设置一个默认的collection
        cloudSolrServer.setDefaultCollection("collection2");
        // 3. 创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 3. 向文档中添加域
        document.addField("id","testSolrCloud");
        document.addField("item_title","测试商品名称");
        document.addField("item_price", 100);
        // 4. 把文档写入索引库
        cloudSolrServer.add(document);
        // 5. 提交
        cloudSolrServer.commit();
    }
}

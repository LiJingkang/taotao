package com.taotao.solrj;

import com.sun.tracing.dtrace.ArgsAttributes;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;


/**
 * Created by Skying on 2017/3/5.
 */
public class TestSolrJ {

    @Test
    public void testAddDocument() throws Exception {
        // 1. 创建一个 SolrServer对象。 创建一个 HttpSolrServer对象
        // 2. 需要指定 Solr 服务的 url。
        // SolrServer 抽象类
        // 默认是 collection1
        SolrServer solrServer = new HttpSolrServer("http://192.168.56.2:8080/solr");
        // 3. 创建一个档对象 SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        // 4. 向文档中添加域，必须有id域，域的名称必须在 schema.xml 中定义
        document.addField("id", "test002");
        document.addField("item_title", "测试商品");
        document.addField("item_price", 1000);
        // 5. 把文档对象写入索引库
        solrServer.add(document);
        // 6. 提交
        solrServer.commit();
    }

    @Test
    // 根据 Id 删除
    public void deleteDocumentById() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.56.2:8080/solr");
        solrServer.deleteById("test001");
        // 提交
        solrServer.commit();
    }

    @Test
    // 根据 查询删除
    public void deleteDocumentByQuery() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.56.2:8080/solr");
        solrServer.deleteByQuery("item_title:测试商品3");
        // 提交
        solrServer.commit();
    }

    // 更新就是添加
}

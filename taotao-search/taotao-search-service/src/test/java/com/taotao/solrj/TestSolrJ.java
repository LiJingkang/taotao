package com.taotao.solrj;

import com.sun.tracing.dtrace.ArgsAttributes;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


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

    // 查询文档
    @Test
    public void searchDocumet() throws Exception {
        // 1. 创建一个 SolrServic对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.56.2:8080/solr");
        // 2. 创建一个 SolrQuery对象
        SolrQuery query = new SolrQuery();
        // 3. 设置查询条件，过滤条件，分页条件，高亮，排序
        // query.set("q", "*:*");
        query.setQuery("手机");
        // 分页条件
        query.setStart(30);
        query.setRows(20);
        // 设置默认搜索域
        query.set("df", "item_keywords");
        // 设置高亮
        query.setHighlight(true);
        // 设置高亮显示的域
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<div>");
        query.setHighlightSimplePost("</div>");
        // 4. 执行查询，得到一个Response 对象
        QueryResponse response = solrServer.query(query);
        // 5. 取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        // 6. 取查询结果总记录数
        System.out.print("查询结果总记录数" + solrDocumentList.getNumFound());

        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            // 取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = "";
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            System.out.println(itemTitle);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
            System.out.println("=====================================================");
        }
    }
}

package com.taotao.content.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Skying on 2017/3/3.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;


    @Override
    public TaotaoResult addContent(TbContent content) {
        // 补全pojo的属性
        content.setCreated(new Date());
        content.setUpdated(new Date());
        // 插入到内容表
        contentMapper.insert(content);

        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentByCid(long cid) {
        // 先查询缓存
        // 添加缓存不能影响正常的业务罗技
        try {
            // 查询缓存
            // 查询到结果，把json转换为List返回
            String json = jedisClient.hget(INDEX_CONTENT,cid + "");
            if (StringUtils.isNotBlank(json)) {
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 缓存中没有命中 需要查询数据亏
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        // 设置查询条件
        criteria.andCategoryIdEqualTo(cid);
        // 执行查询
        List<TbContent> list = contentMapper.selectByExample(example);
        // 把结果添加到缓存
        try {
            // 加入缓存
            jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return list;
    };
}

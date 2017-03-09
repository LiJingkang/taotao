package com.taotao.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * 商品管理服务
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    // 单例，我们可以根据类型来注入
    private JmsTemplate jmsTemplate;
    @Autowired
    private JedisClient jedisClient;

    // 根据名称来注入，如果名称匹配不上 再根据类名
    @Resource(name = "itemAddtopic")
    private Destination destination;

    @Value("${ITEM_INFO}")
    private String ITEM_INFO;

    @Value("${ITEM_EXPIRE}")
    private Integer ITEM_EXPIRE;

    @Override
    public TbItem getItemById(long itemId) {

        try {
            // 查询数据库之前先查询缓存
            String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":BASE");
            if (StringUtils.isNotBlank(json)) {
                // 把json 转换为对象
                TbItem tbItem = JsonUtils.jsonToPojo(json,TbItem.class);
                return tbItem;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 缓存中没有查询数据库
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        try {
            // 把查询结果添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId + ":BASE", JsonUtils.objectToJson(item));
            // 设置过期事件，提高缓存利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId + ":BASE", ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        // 设置分页信息
        PageHelper.startPage(page, rows);
        // 执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        // 取查询结果
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        result.setTotal(pageInfo.getTotal());
        // 返回结果
        return result;
    }

    @Override
    public TaotaoResult addItem(TbItem item, String desc) {

        // 1. 生成商品id
        final long itemId = IDUtils.genItemId();
        // 2. 补全item属性
        item.setId(itemId);
        // 商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 3. 向商品表插入数据
        itemMapper.insert(item);
        // 4. 创建一个商品描述表对应的pojo
        TbItemDesc itemDesc = new TbItemDesc();
        // 5. 补全pojo的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        itemDesc.setCreated(new Date());
        // 6. 向商品描述表插入数据
        itemDescMapper.insert(itemDesc);
        // 向 Activemq 发送消息添加事件
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });
        // 7. 返回结果
        return TaotaoResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {
        try {
            // 查询数据库之前先查询缓存
            String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":DESC");
            if (StringUtils.isNotBlank(json)) {
                // 把json 转换为对象
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json,TbItemDesc.class);
                return tbItemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 缓存中没有查找数据库
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        try {
            // 把查询结果添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
            // 设置过期事件，提高缓存利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId + ":DESC", ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemDesc;
    }
}

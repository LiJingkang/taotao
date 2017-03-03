package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbItemCatExample;
import org.apache.log4j.helpers.FormattingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Skying on 2017/3/2.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(long parentId) {

        // 根据parentId查询子节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        // 设置查询条件
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // 执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            // 写反了，找了半天
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            // 添加到结果列表
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        // 1. 创建一个pojo对象
        TbContentCategory contentCategory = new TbContentCategory();
        // 2. 补全对象属性
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        // 状态，可选值：1（正常），2（删除）
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        // 3. 插入到数据库
        contentCategoryMapper.insert(contentCategory);
        // 判断父结点的状态
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            // 如果父结点为叶子结点应该改为父结点
            parent.setIsParent(true);
            // 更新父结点
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        // 4. 返回结构
        return TaotaoResult.ok(contentCategory);
    }
}

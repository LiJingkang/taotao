package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by Skying on 2017/3/1.
 */
public interface ItemCatService {

	List<EasyUITreeNode> getItemCatList(long parentId);
}

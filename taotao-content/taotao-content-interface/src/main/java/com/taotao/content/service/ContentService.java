package com.taotao.content.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by Skying on 2017/3/3.
 */
public interface ContentService {

    TaotaoResult addContent(TbContent content);

    List<TbContent> getContentByCid(long cid);
}

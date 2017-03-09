package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;

/**
 * Created by Skying on 2017/3/9.
 */
public class Item extends TbItem {

    // 构造方法
    public Item(TbItem tbItem) {
        // 属性初始化
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());

    }

    // 重写get 方法 来添加属性
    public String[] getImages() {
        // 如果不是空，并且不是空字符串
        if (this.getImage() != null & !"".equals(this.getImage())) {
            String image2 = this.getImage();
            String[] strings = image2.split(",");
            return strings;
        }
        return null;
    }
}

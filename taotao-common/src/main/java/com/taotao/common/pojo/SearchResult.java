package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Skying on 2017/3/6.
 */
public class SearchResult implements Serializable{

    private int totalPages;

    private List<SearchItem> itemList;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}

package com.example.guanzhuli.icart.data;

import java.util.List;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class CustomerCart{
    String mMobile;
    List<Item> mItemArrayList;
    double  total;
    public CustomerCart(String mobile, List<Item> items, double total) {
        this.mMobile = mobile;
        this.mItemArrayList = items;
        this.total = total;
    }

    public String getMobile() {
        return mMobile;
    }

    public List<Item> getItemArrayList() {
        return mItemArrayList;
    }
}


package com.example.guanzhuli.icart.data;

import java.util.ArrayList;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class CustomerCart {
    String mName;
    String mMobile;
    ArrayList<Item> mItemArrayList;
    int total;
    CustomerCart(String name, String mobile, ArrayList<Item> items) {
        this.mName = name;
        this.mMobile = mobile;
        this.mItemArrayList = items;
        int total = 0;
        for (int i  = 0; i < mItemArrayList.size(); i++) {
            total += mItemArrayList.get(i).price * mItemArrayList.get(i).quantity;
        }
        this.total = total;
    }
}


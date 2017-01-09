package com.example.guanzhuli.icart.data;

import java.io.Serializable;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class Item implements Serializable {
    private String mId;
    private String mName;
    private String mImageurl;
    private String mDescription;
    private int mQuantity;
    private double mPrice;
    private int mMaxQuant;

    public Item() {
    }

    Item(String id, String name, String imageUrl, double price, int quantity) {
        this.mId = id;
        this.mName = name;
        this.mImageurl = imageUrl;
        this.mPrice = price;
        this.mQuantity = quantity;
    }

    public Item(String id, String name, String imageUrl, String description, int quantity, double price) {
        this.mId = id;
        this.mName = name;
        this.mImageurl = imageUrl;
        this.mDescription = description;
        this.mQuantity = quantity;
        this.mPrice = price;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }

    public void setQuantity(int quantity){
        this.mQuantity = quantity;
    }

    public void setImageurl(String imageurl) {
        this.mImageurl = imageurl;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getQuantity(){
        return mQuantity;
    }

    public String getImageurl() {
        return mImageurl;
    }

    public int getMaxQuant() {
        return mMaxQuant;
    }

    public void setMaxQuant(int maxQuant) {
        mMaxQuant = maxQuant;
    }
}

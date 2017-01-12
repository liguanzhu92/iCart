package com.example.guanzhuli.icart.data;

/**
 * Created by Guanzhu Li on 1/11/2017.
 */
public class NewArrivalItem {
    private int mId;
    private String mName;
    private double mPrice;
    private int mResource;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public int getResource() {
        return mResource;
    }

    public void setResource(int resource) {
        mResource = resource;
    }



    public NewArrivalItem(int id, String name, double price, int resource) {
        mId = id;
        mName = name;
        mPrice = price;
        mResource = resource;
    }
}

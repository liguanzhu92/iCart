package com.example.guanzhuli.icart.data;

/**
 * Created by Xu on 12/31/2016.
 */
public class Category {
    private String mImageUrl;
    private String mName;
    private String mId;

    public Category(String imageUrl, String name, String id) {
        mImageUrl = imageUrl;
        mName = name;
        mId = id;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }
}

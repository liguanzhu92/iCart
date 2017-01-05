package com.example.guanzhuli.icart.data;

/**
 * Created by Guanzhu Li on 1/4/2017.
 */
public class TopSeller {
    private String id;
    private String name;
    private String deal;
    private String rating;
    private String imageUrl;

    public TopSeller() {}

    public TopSeller(String id, String name, String deal, String rating, String imageUrl) {
        this.id = id;
        this.name = name;
        this.deal = deal;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeal() {
        return deal;
    }

    public String getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

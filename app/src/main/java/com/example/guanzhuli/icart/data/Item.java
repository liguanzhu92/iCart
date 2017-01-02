package com.example.guanzhuli.icart.data;

/**
 * Created by Guanzhu Li on 12/31/2016.
 */
public class Item {
    String id;
    String name;
    String imageUrl;
    String destription;
    int quantity;
    double price;
    public Item(String id, String name, String imageUrl, String description, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.destription = description;
        this.quantity = quantity;
        this.price = price;
    }

/*    Item(String id, String name, String imageUrl, double price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }*/
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDestription() {
        return destription;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}

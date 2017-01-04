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
    public Item() {

    }
    public Item(String id, String name, String imageUrl, String description, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.destription = description;
        this.quantity = quantity;
        this.price = price;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.destription = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    Item(String id, String name, String imageUrl, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
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

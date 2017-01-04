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
    public void putId(String id) {
        this.id = id;
    }

    public void putName(String name) {
        this.name = name;
    }

    public void putDestription(String description) {
        this.destription = description;
    }

    public void putPrice(double price) {
        this.price = price;
    }

    public void putQuantity(int quantity){
        this.quantity = quantity;
    }

    public void putImageUrl(String imageUrl) {
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

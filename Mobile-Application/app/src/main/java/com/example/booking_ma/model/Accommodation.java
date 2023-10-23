package com.example.booking_ma.model;

public class Accommodation {
    private int imageResource;
    private String name;
    private String price;
    private float stars;

    public Accommodation(int imageResource, String name, String price, float stars) {
        this.imageResource = imageResource;
        this.name = name;
        this.price = price;
        this.stars = stars;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public float getStars() {
        return stars;
    }
}

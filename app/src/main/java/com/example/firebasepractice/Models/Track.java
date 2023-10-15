package com.example.firebasepractice.Models;

public class Track {

    private String id;
    private String name;
    private int rating;

    public Track(String id, String name, int rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public Track() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

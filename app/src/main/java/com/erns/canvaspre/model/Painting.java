// Painting.java
package com.erns.canvaspre.model;

public class Painting {
    private int id;
    private String artist;
    private String title;
    private String imageLink;
    private int roomId;
    private float x;
    private float y;
    private String description;

    public Painting(int id, String artist, String title, String imageLink, int roomId, float x, float y, String description) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.imageLink = imageLink;
        this.roomId = roomId;
        this.x = x;
        this.y = y;
        this.description = description;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getRoomId() {
        return roomId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getDescription() {
        return description;
    }
}

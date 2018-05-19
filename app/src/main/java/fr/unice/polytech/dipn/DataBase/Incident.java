package fr.unice.polytech.dipn.DataBase;

import java.io.Serializable;

/**
 * Created by Margoulax on 27/04/2018.
 */


public class Incident implements Serializable{

    private int id;

    private String author;

    private String title;

    private int advancement;

    private double latitude;

    private double longitude;

    private int importance;

    private String description;

    private String date;

    private byte[] image;

    public Incident() {


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAdvancement() {
        return advancement;
    }

    public void setAdvancement(int advancement) {
        this.advancement = advancement;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return title+" -> "+advancement;
    }
}

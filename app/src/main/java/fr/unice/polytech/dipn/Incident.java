package fr.unice.polytech.dipn;

import android.location.Location;

import java.util.Date;

/**
 * Created by François on 27/04/2018.
 */

public class Incident {
    private int id;
    private String author;
    private String title;
    private int advancement;
    private double latitude;
    private double longitude;
    private int importance;
    private String description;
    private long date;

    public Incident(int id, String title, String author, int advancement, double latitude, double longitude,
                    int importance, String description, long date) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.advancement = advancement;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.description = description;
        this.importance = importance;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}

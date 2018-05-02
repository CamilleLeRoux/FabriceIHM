package fr.unice.polytech.dipn.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

import java.util.Date;

/**
 * Created by Margoulax on 27/04/2018.
 */

@Entity
public class Incident {
    @PrimaryKey
    private int id;
    @ColumnInfo
    private String author;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private int advancement;
    @ColumnInfo
    private double latitude;
    @ColumnInfo
    private double longitude;
    @ColumnInfo
    private int importance;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private String date;

    @Ignore
    public Incident(int id, String title, String author, int advancement, double latitude, double longitude,
                    int importance, String description, String date) {
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

    public Incident(String title) {
        this.title = title;
        this.id = 1000;
        this.author = "Admin";
        this.advancement = 1;
        this.latitude = 500;
        this.longitude = 500;
        this.date = "00-Tes-0000";
        this.description = "Test Ajout";
        this.importance = 1;
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
}

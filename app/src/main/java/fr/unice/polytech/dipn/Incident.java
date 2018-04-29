package fr.unice.polytech.dipn;

/**
 * Created by François on 27/04/2018.
 */

public class Incident {
    private int id;
    private String author;
    private String title;
    private int resolvedLevel;

    public Incident(int id, String title, String author, int resolvedLevel) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.resolvedLevel = resolvedLevel;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }
    public int getResolvedLevel() {
        return this.resolvedLevel;
    }
}

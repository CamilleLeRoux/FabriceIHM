package fr.unice.polytech.dipn;

/**
 * Created by François on 29/04/2018.
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Data {
    private static Data ourInstance = new Data();
    private ArrayList<Incident> incidents;
    private int lastId = 0;

    public static Data getInstance() {
        return ourInstance;
    }

    private Data() {
        //test tasks
        Date currentTime = Calendar.getInstance().getTime();
        this.incidents = new ArrayList<Incident>();
        incidents.add(new Incident(1, "Chaise Cassée", "Charles",1,1,2,1,"YOLO",currentTime.getTime()));
        incidents.add(new Incident(2, "Inondation", "Camille",1,1,2,1,"YOLO",currentTime.getTime()));
        incidents.add(new Incident(3, "Ampoule claquée", "Camille",2,1,2,1,"YOLO",currentTime.getTime()));
        incidents.add(new Incident(4, "Nombre de prise de courants","HeavyHammer42",2,1,2,1,"YOLO",currentTime.getTime()));
        incidents.add(new Incident(5, "Rétroprojecteur déféctueux","Francis",3,1,2,1,"YOLO",currentTime.getTime()));
        this.lastId = 5;
    }

    public static void addIncident(String title, String author, int advancement, double latitude, double longitude,int importance, String description, Date date) {
        ourInstance.incidents.add(
                new Incident(ourInstance.lastId + 1, title, author, advancement, latitude, longitude, importance, description, date.getTime())
        );
    }

    public ArrayList<Incident> getDataFilteredByLevel(int level) {
        ArrayList<Incident> filtered = new ArrayList<Incident>();
        for (Incident i : ourInstance.incidents) {
            if (i.getAdvancement() == level) {
                filtered.add(i);
            }
        }
        return filtered;
    }
}


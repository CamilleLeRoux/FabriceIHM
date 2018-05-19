package fr.unice.polytech.dipn;

/**
 * Created by Margoulax on 29/04/2018.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.unice.polytech.dipn.DataBase.Incident;

public class Data {
    private static Data ourInstance = new Data();
    private ArrayList<Incident> incidents;
    private int lastId = 0;

    private Data() {
        //test tasks
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(currentTime);
        byte[] byteArray = "Test".getBytes();
        this.incidents = new ArrayList<Incident>();

        this.lastId = 5;
    }

    public static Data getInstance() {
        return ourInstance;
    }

    public static void addIncident(String title, String author, int advancement, double latitude, double longitude,int importance, String description, String date, byte[] image) {
        //ourInstance.incidents.add(
        //   new Incident(title, author, advancement, latitude, longitude, importance, description, date)
        // );
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


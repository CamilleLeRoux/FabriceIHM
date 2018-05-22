package fr.unice.polytech.dipn;

/**
 * Created by Margoulax on 29/04/2018.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.unice.polytech.dipn.DataBase.Incident;

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
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(currentTime);
        byte[] byteArray = "Test".getBytes();
        this.incidents = new ArrayList<Incident>();
        incidents.add(new Incident("Chaise Cassée", "Charles",1,1,2,"O+999",1,"YOLO",formattedDate,byteArray));
        incidents.add(new Incident("Inondation", "Camille",1,1,2,"O+999",3,"YOLO",formattedDate,byteArray));
        incidents.add(new Incident("Ampoule claquée", "Camille",2,1,2,"O+999",2,"YOLO",formattedDate,byteArray));
        incidents.add(new Incident("Nombre de prise de courants","HeavyHammer42",2,1,2,"O+999",1,"YOLO",formattedDate,byteArray));
        incidents.add(new Incident("Rétroprojecteur déféctueux","Francis",3,1,2,"O+999",1,"YOLO",formattedDate,byteArray));
        this.lastId = 5;
    }

    public static void addIncident(String title, String author, int advancement, double latitude, double longitude, String room,int importance, String description, String date, byte[] image) {
        ourInstance.incidents.add(
                new Incident( title, author, advancement, latitude, longitude, room, importance, description, date,image)
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


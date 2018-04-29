package fr.unice.polytech.dipn;

/**
 * Created by François on 29/04/2018.
 */

import java.util.ArrayList;

public class Data {
    private static Data ourInstance = new Data();
    private ArrayList<Incident> incidents;
    private int lastId = 0;

    public static Data getInstance() {
        return ourInstance;
    }

    private Data() {
        //test tasks
        this.incidents = new ArrayList<Incident>();
        incidents.add(new Incident(1, "Chaise Cassée", "Charles",1));
        incidents.add(new Incident(2, "Inondation", "Camille",1));
        incidents.add(new Incident(3, "Ampoule claquée", "Camille",2));
        incidents.add(new Incident(4, "Nombre de prise de courants","HeavyHammer42",2));
        incidents.add(new Incident(5, "Rétroprojecteur déféctueux","Francis",3));
        this.lastId = 5;
    }

    public static void addIncident(String title, String author, int resolvedLevel) {
        ourInstance.incidents.add(
                new Incident(ourInstance.lastId + 1, title, author, resolvedLevel)
        );
    }

    public ArrayList<Incident> getDataFilteredByLevel(int level) {
        ArrayList<Incident> filtered = new ArrayList<Incident>();
        for (Incident i : ourInstance.incidents) {
            if (i.getResolvedLevel() == level) {
                filtered.add(i);
            }
        }
        return filtered;
    }
}


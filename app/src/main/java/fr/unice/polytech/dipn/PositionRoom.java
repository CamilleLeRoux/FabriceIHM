package fr.unice.polytech.dipn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 22/05/2018.
 */

public enum PositionRoom {
    EP135("Batiment E","E+135"),
    EM103("Batiment E","E-103"),
    EM105("Batiment E","E+105"),
    EM106("Batiment E","E+106"),
    OP109("Batiment O","0+109"),
    OP108("Batiment O","0+108"),
    OP110("Batiment O","0+110"),
    OP107("Batiment O","0+107"),
    AmphiF("Batiment Forum","Amphi Forum"),
    AmphiL("Batiment Luciole","Amphi Luciole");

    private final String bat;
    private final String room;

    PositionRoom(String bat, String room) {
        this.bat = bat;
        this.room = room;
    }

    public String getBat() {
        return bat;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return room;
    }

    static public List<String> getRoomByBat(String bat){
        List<String> room = new ArrayList<String>();
        for (PositionRoom p : PositionRoom.values()){
            if(p.getBat().equals(bat)){
                room.add(p.getRoom());
            }
        }
        return room;
    }
}

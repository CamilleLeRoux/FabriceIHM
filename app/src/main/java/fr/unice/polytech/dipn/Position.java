package fr.unice.polytech.dipn;

/**
 * Created by user on 14/05/2018.
 */

public enum Position {
    BatimentE("Batiment E",43.616040, 7.072189),
    BatimentO("Batiment O",43.615572, 7.071681),
    BatimentF("Batiment Forum",43.615248, 7.071139),
    BatimentL("Batiment Luciole",43.617087, 7.064373);

    private final String name;
    private final double lat;
    private final double lon;

    Position(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override public String toString(){
        return name;
    }
}

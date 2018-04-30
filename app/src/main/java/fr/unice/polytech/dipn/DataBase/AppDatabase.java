package fr.unice.polytech.dipn.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by user on 30/04/2018.
 */

@Database(entities = {Incident.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IncidentDAO incidentDAO();
}

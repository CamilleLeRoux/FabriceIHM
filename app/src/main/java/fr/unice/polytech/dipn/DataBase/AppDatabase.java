package fr.unice.polytech.dipn.DataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by user on 30/04/2018.
 */

@Database(entities = {Incident.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static RoomDatabase.Callback roomDatabaseCallback =
            new RoomDatabase.Callback() {

                public void onOpen(SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(incidentInstance).execute();
                }
            };

    public abstract IncidentDAO incidentDAO();

    public static AppDatabase incidentInstance;

    public static AppDatabase getDatabase(final Context context) {
        if (incidentInstance == null) {
            synchronized (AppDatabase.class) {
                if (incidentInstance == null) {
                    incidentInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "incident_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return incidentInstance;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final IncidentDAO dao;

        PopulateDbAsync(AppDatabase db) {
            dao = db.incidentDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            dao.deleteAll();
            Incident incident = new Incident(100,"Hello","Admin",1,0,0,3,"Populate","00-Nul-0000");
            dao.insert(incident);
            return null;
        }
    }

}

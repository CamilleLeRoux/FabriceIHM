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

@Database(entities = {Incident.class}, version = 2)
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
                            .fallbackToDestructiveMigration()
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
            Incident incident = new Incident("Hello","Admin",1,0,0,3,"Populate","00-Nul-0000");
            Incident incident2 = new Incident("Hello2","Admin",2,0,0,2,"Populate2","02-Nul-0000");
            Incident incident3 = new Incident("Hello3","Admin",2,0,0,2,"Populate3","03-Nul-0000");
            Incident incident4 = new Incident("Hello4","Admin",1,0,0,1,"Populate4","04-Nul-0000");
            Incident incident5 = new Incident("Hello5","Admin",3,0,0,3,"Populate5","05-Nul-0000");
            Incident incident6 = new Incident("Hello6","Admin",3,0,0,3,"Populate6","06-Nul-0000");
            dao.insert(incident);
            dao.insert(incident2);
            dao.insert(incident3);
            dao.insert(incident4);
            dao.insert(incident5);
            dao.insert(incident6);
            return null;
        }
    }

}

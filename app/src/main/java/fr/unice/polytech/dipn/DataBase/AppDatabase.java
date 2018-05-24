package fr.unice.polytech.dipn.DataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;

import fr.unice.polytech.dipn.R;

/**
 * Created by user on 30/04/2018.
 */

@Database(entities = {Incident.class}, version = 5)
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
            byte[] byteArray = "Test".getBytes();
            Incident incident = new Incident("Chaise cassée","Charles",1,41.842004, -89.485902,"O+999",1,"Une des chaise n'a plus de dossier. Elle a été mise au fond de la classe","05-05-2018",byteArray);
            Incident incident2 = new Incident("Inondation","Admin",2,0,0,"O+999",3,"Un des robinets des toilettes est cassé ce qui provoque une inondation générale","26-04-2018",byteArray);
            Incident incident3 = new Incident("Rétroprojecteur défectueux","Admin",3,0,0,"O+999",2,"Le rétroprojecteur emet un petit bruit toutes les dizaines de secondes","04-05-2018",byteArray);
            Incident incident4 = new Incident("Porte qui grince","François",1,44.3333,1.2167,"O+999",1,null,"01-05-2018",byteArray);
            Incident incident5 = new Incident("Manque de stylos","Camille",3,0,0,"O+999",1,"Il n'y a plus de stylos bleu pour le tableau","02-05-2018",byteArray);
            Incident incident6 = new Incident("Vitre brisée","Albert",1,0,0,"O+999",3,"Une vitre près de l'amphi forum a été brisée ce qui laisse apparents des morceaux de verre coupant","29-04-2018",byteArray);
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

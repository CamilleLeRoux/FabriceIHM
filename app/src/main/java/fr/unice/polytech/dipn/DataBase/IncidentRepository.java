package fr.unice.polytech.dipn.DataBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by user on 02/05/2018.
 */

public class IncidentRepository {

    List<Incident> test;
    private IncidentDAO incidentDAO;
    private LiveData<List<Incident>> allIncident;

    public IncidentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        incidentDAO = db.incidentDAO();
        allIncident = incidentDAO.getAllIncident();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("incident");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Incident climate = postSnapshot.getValue(Incident.class);
                    allIncident.getValue().add(climate);
                }


            }


            @Override
            public void onCancelled(DatabaseError firebaseError) {
                System.out.println("firebase error :" + firebaseError.getDetails());
            }
        });

    }

    LiveData<List<Incident>> getAllIncident() {
        return allIncident;
    }

    public void insert (Incident incident) {
        new insertAsyncTask(incidentDAO).execute(incident);
    }

    private static class insertAsyncTask extends AsyncTask<Incident, Void, Void> {

        private IncidentDAO mAsyncTaskDao;

        insertAsyncTask(IncidentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Incident... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}

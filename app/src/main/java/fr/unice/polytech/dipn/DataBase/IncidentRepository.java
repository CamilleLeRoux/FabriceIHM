package fr.unice.polytech.dipn.DataBase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by user on 02/05/2018.
 */

public class IncidentRepository {

    private IncidentDAO incidentDAO;
    private LiveData<List<Incident>> allIncident;

    public IncidentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        incidentDAO = db.incidentDAO();
        allIncident = incidentDAO.getAllIncident();
    }

    LiveData<List<Incident>> getAllIncident() {
        return allIncident;
    }

    public void insert(Incident incident) {
        new insertAsyncTask(incidentDAO).execute(incident);
    }

    public void delete(Incident incident) {
        new deleteAsyncTask(incidentDAO).execute(incident);
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

    private static class deleteAsyncTask extends AsyncTask<Incident, Void, Void> {

        private IncidentDAO mAsyncTaskDao;

        deleteAsyncTask(IncidentDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Incident... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}

package fr.unice.polytech.dipn.DataBase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by user on 02/05/2018.
 */

public class IncidentViewModel extends AndroidViewModel {

    private IncidentRepository repository;
    private LiveData<List<Incident>> allIncident;

    public IncidentViewModel(Application application) {
        super(application);
        this.repository = new IncidentRepository(application);
        this.allIncident = this.repository.getAllIncident();
    }

    public LiveData<List<Incident>> getAllIncident() {
        return allIncident;
    }

    public void insert(Incident incident) {
        this.repository.insert(incident);
    }
}

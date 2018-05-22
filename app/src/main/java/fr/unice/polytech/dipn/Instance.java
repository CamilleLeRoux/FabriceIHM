package fr.unice.polytech.dipn;

import fr.unice.polytech.dipn.DataBase.IncidentViewModel;

/**
 * Created by user on 22/05/2018.
 */

public class Instance {
    private static Instance instance;
    private IncidentViewModel ivm;
    private String session;

    public static Instance getInstance() {
        if (instance == null) {
            instance = new Instance();
        }
        return instance;
    }

    private Instance() {
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public IncidentViewModel getIncidentViewModel() {return this.ivm;}

    public void setIncidentViewModel (IncidentViewModel incidentViewModel) {
        this.ivm = incidentViewModel;
    }
}

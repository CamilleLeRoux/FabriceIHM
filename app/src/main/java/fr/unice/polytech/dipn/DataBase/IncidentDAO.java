package fr.unice.polytech.dipn.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by user on 30/04/2018.
 */

@Dao
public interface IncidentDAO {

    @Query("SELECT * FROM incident")
    LiveData<List<Incident>> getAllIncident();

    @Query("SELECT * FROM incident WHERE id IN (:incidentIds)")
    List<Incident> loadAllIncidentByIds(int[] incidentIds);

    @Insert
    void insertAll(Incident... incidents);

    @Insert
    void insert(Incident incident);

    @Delete
    void delete(Incident incident);

    @Query("DELETE FROM incident")
    void deleteAll();


}

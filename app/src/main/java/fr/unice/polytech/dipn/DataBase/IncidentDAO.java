package fr.unice.polytech.dipn.DataBase;

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
    List<Incident> getAll();

    @Query("SELECT * FROM incident WHERE id IN (:incidentIds)")
    List<Incident> loadAllByIds(int[] incidentIds);

    @Insert
    void insertAll(Incident... incidents);

    @Delete
    void delete(Incident incident);


}

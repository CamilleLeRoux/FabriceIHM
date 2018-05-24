package fr.unice.polytech.dipn;

import android.arch.lifecycle.LiveData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.unice.polytech.dipn.DataBase.Incident;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LiveData<List<Incident>> incidentList = Instance.getInstance().getIncidentViewModel().getAllIncident();

        List<Incident> incidents = incidentList.getValue();

        for (Incident i : incidents){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(i.getLatitude(),i.getLongitude())).title(i.getTitle()));
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.616368, 7.069197), 15));

    }
}

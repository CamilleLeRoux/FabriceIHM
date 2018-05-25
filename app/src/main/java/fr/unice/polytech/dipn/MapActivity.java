package fr.unice.polytech.dipn;

import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.unice.polytech.dipn.DataBase.Incident;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final TextView title = findViewById(R.id.titleMap);
        title.setTextColor(Color.WHITE);

        Switch switchType = findViewById(R.id.switchType);
        switchType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }else {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LiveData<List<Incident>> incidentList = Instance.getInstance().getIncidentViewModel().getAllIncident();

        final List<Incident> incidents = incidentList.getValue();

        for (Incident i : incidents){
            this.googleMap.addMarker(new MarkerOptions().position(new LatLng(i.getLatitude(),i.getLongitude())).title(i.getTitle()));
        }
        this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.616368, 7.069197), 15));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                for (Incident i : incidents){
                    if (i.getTitle().equals(marker.getTitle())){
                        Intent intent = new Intent(getBaseContext(), IncidentDetails.class);
                        intent.putExtra("Incident", i);
                        startActivityForResult(intent, 0);
                        return true;
                    }
                }
                return false;

            }
        });

    }
}

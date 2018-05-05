package fr.unice.polytech.dipn;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import fr.unice.polytech.dipn.DataBase.Incident;
import fr.unice.polytech.dipn.DataBase.IncidentViewModel;


public class IncidentForm extends AppCompatActivity implements OnMapReadyCallback {



    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private IncidentViewModel incidentViewModel;
    private GoogleMap googleMap;
    private Location location;
    private LocationManager mLocationManager;




    @Override
    protected void onCreate (Bundle savedInstanceState){

        incidentViewModel = ViewModelProviders.of(this).get(IncidentViewModel.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_form);

        final Button save = (Button) findViewById(R.id.save);

        final EditText editAuthor = findViewById((R.id.editAuthor));
        final EditText editTitle = findViewById((R.id.editTitle));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent replyIntent = new Intent();
                if ((TextUtils.isEmpty(editAuthor.getText()) && TextUtils.isEmpty(editTitle.getText())) || TextUtils.isEmpty(editAuthor.getText()) || TextUtils.isEmpty(editTitle.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String author = editAuthor.getText().toString();
                    String title = editTitle.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, title);
                    setResult(RESULT_OK, replyIntent);
                    Incident word = new Incident(title);
                    incidentViewModel.insert(word);
                    Intent intent = new Intent(IncidentForm.this, IncidentList.class);
                    startActivity(intent);
                }
                finish();
                }
        });
    }


    //Getting current location
    private void getCurrentLocation() {
        //googleMap.clear();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }

        /*boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isNetworkEnabled) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        if (isGPSEnabled) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }*/
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        //getCurrentLocation();
        LatLng position = new LatLng(43.615788,7.072512);
        googleMap.addMarker(new MarkerOptions().position(position)
                .title("Polytech Batiment E"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
    }


}

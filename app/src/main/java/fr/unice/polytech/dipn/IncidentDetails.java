package fr.unice.polytech.dipn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.unice.polytech.dipn.DataBase.Incident;

public class IncidentDetails extends AppCompatActivity implements OnMapReadyCallback {

    private Incident incident;
    private GoogleMap googleMap;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_details);
        this.incident = (Incident) getIntent().getSerializableExtra("Incident");

        final TextView title = findViewById(R.id.detailTitle);
        final TextView author = findViewById(R.id.detailAuthor);
        final TextView date = findViewById(R.id.detailDate);
        final TextView description = findViewById(R.id.detailDescription);
        final ImageView icon = findViewById(R.id.detailIcon);
        final ImageView image = findViewById(R.id.detailImage);
        if (image != null) {
            image.setVisibility(View.VISIBLE);
        }
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setProgress(1 + 33 * incident.getAdvancement());

        title.setText(incident.getTitle());
        author.setText(incident.getAuthor());
        date.setText(incident.getDate());
        description.setText(incident.getDescription());

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detailMap);
        mapFragment.getMapAsync(this);

       // Bitmap bitmap = BitmapFactory.decodeByteArray(incident.getImage(), 0, incident.getImage().length);
        // image.setImageBitmap(bitmap);

        switch(incident.getImportance()) {
            case 1:
                icon.setImageResource(R.drawable.emergency1);
                break;
            case 2:
                icon.setImageResource(R.drawable.emergency2);
                break;
            case 3:
                icon.setImageResource(R.drawable.emergency3);
                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng position = new LatLng(incident.getLatitude(),incident.getLongitude());
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(position)
                .title(Position.getNameLatLon(incident.getLatitude(),incident.getLongitude())));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18));

    }

}

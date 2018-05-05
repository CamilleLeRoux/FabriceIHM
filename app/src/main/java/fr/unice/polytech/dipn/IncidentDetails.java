package fr.unice.polytech.dipn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fr.unice.polytech.dipn.DataBase.Incident;

public class IncidentDetails extends AppCompatActivity {

    private Incident incident;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_details);
        this.incident = (Incident) getIntent().getSerializableExtra("Incident");

        final TextView title = findViewById(R.id.detailTitle);

        title.setText(incident.getTitle());

    }
}

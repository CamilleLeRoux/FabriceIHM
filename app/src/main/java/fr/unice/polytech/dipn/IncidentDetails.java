package fr.unice.polytech.dipn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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
        final TextView author = findViewById(R.id.detailAuthor);
        final TextView date = findViewById(R.id.detailDate);
        final TextView description = findViewById(R.id.detailDescription);
        final ImageView icon = findViewById(R.id.detailIcon);

        title.setText(incident.getTitle());
        author.setText(incident.getAuthor());
        date.setText(incident.getDate());
        description.setText(incident.getDescription());

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
}

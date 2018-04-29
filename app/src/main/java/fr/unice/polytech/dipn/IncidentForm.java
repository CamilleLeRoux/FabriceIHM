package fr.unice.polytech.dipn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IncidentForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_form);

        final Button save = (Button) findViewById(R.id.save);

        final EditText editAuthor = findViewById((R.id.editAuthor));
        final EditText editTitle = findViewById((R.id.editTitle));


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String author = editAuthor.getText().toString();
                String title = editTitle.getText().toString();
                if (!author.equals("") && !title.equals("")) {
                    Data.addIncident(title,author,1);
                    Intent intent = new Intent(IncidentForm.this, IncidentList.class);
                    startActivity(intent);
                }
            }
        });
    }
}

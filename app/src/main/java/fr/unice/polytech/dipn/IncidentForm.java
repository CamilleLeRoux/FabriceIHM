package fr.unice.polytech.dipn;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.unice.polytech.dipn.DataBase.AppDatabase;
import fr.unice.polytech.dipn.DataBase.Incident;
import fr.unice.polytech.dipn.DataBase.IncidentViewModel;

import static fr.unice.polytech.dipn.IncidentPreviewFragment.NEW_WORD_ACTIVITY_REQUEST_CODE;

public class IncidentForm extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private IncidentViewModel incidentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        incidentViewModel = ViewModelProviders.of(this).get(IncidentViewModel.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_form);

        final Button save = (Button) findViewById(R.id.save);

        final EditText editAuthor = findViewById((R.id.editAuthor));
        final EditText editTitle = findViewById((R.id.editTitle));




        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(currentTime);
                String author = editAuthor.getText().toString();
                String title = editTitle.getText().toString();
                if (!author.equals("") && !title.equals("")) {
                    Data.addIncident(title,author,1,1,2,1,"YOLO",formattedDate);
                    Intent intent = new Intent(IncidentForm.this, IncidentList.class);
                    startActivity(intent);
                }
            }
        });*/

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if ((TextUtils.isEmpty(editAuthor.getText()) && TextUtils.isEmpty(editTitle.getText())) || TextUtils.isEmpty(editAuthor.getText()) || TextUtils.isEmpty(editTitle.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                    System.out.println("EMPTY !!!!!!!!!!!!!!!!!!");
                } else {
                    String author = editAuthor.getText().toString();
                    String title = editTitle.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, title);
                    setResult(RESULT_OK, replyIntent);
                    System.out.println("WORKS !!!!!!!!!!!!!!!!!!");
                    Incident word = new Incident(title);
                    incidentViewModel.insert(word);
                    Intent intent = new Intent(IncidentForm.this, IncidentList.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }
}

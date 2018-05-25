package fr.unice.polytech.dipn;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
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
import fr.unice.polytech.dipn.DataBase.IncidentViewModel;

public class IncidentDetails extends AppCompatActivity implements OnMapReadyCallback {

    private Incident incident;
    private IncidentViewModel ivm;
    private GoogleMap googleMap;
    private int note;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_details);
        this.incident = (Incident) getIntent().getSerializableExtra("Incident");
        this.ivm = Instance.getInstance().getIncidentViewModel();

        final TextView title = findViewById(R.id.detailTitle);
        final TextView author = findViewById(R.id.detailAuthor);
        final TextView date = findViewById(R.id.detailDate);
        final TextView description = findViewById(R.id.detailDescription);
        final ImageView icon = findViewById(R.id.detailIcon);
        final ImageView image = findViewById(R.id.detailImage);
        final TextView batiment = findViewById(R.id.detailBat);
        final TextView Tbatiment = findViewById(R.id.textBat);
        final TextView salle = findViewById(R.id.detailRoom);
        final TextView Tsalle = findViewById(R.id.textRoom);

        final ImageView ileft = findViewById(R.id.detailLeft);
        final ImageView iright = findViewById(R.id.detailRight);
        final ImageView isuppr = findViewById(R.id.detailSuppr);

        if(Instance.getInstance().getSession().equals("user")) {
            ileft.setVisibility(View.INVISIBLE);
            iright.setVisibility(View.INVISIBLE);
            isuppr.setVisibility(View.INVISIBLE);
        }

        refreshProgressBar();

        ileft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incident.getAdvancement()>1) {
                    incident.setAdvancement(incident.getAdvancement()-1);
                    System.out.println("New state: "+incident.getAdvancement());
                    refreshProgressBar();
                    if (Instance.getInstance().getSession().equals("technic") || Instance.getInstance().getSession().equals("admin"))
                    {
                        notificationCall(0);
                    }
                }
            }
        });
        iright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(incident.getAdvancement()<3) {
                    incident.setAdvancement(incident.getAdvancement()+1);
                    System.out.println("New state: "+incident.getAdvancement());
                    refreshProgressBar();
                    if (Instance.getInstance().getSession().equals("technic") || Instance.getInstance().getSession().equals("admin")) {
                        notificationCall(1);
                    }
                }
            }
        });
        isuppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(IncidentDetails.this, R.style.MyAlertDialogStyle);

                builder.setMessage(R.string.popup_suppress_title);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ivm.delete(incident);
                        Intent intent = new Intent(getBaseContext(), IncidentList.class);
                        startActivity(intent);
                        if (Instance.getInstance().getSession().equals("technic") || Instance.getInstance().getSession().equals("admin")) {
                            notificationCall(2);
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                builder.show();

            }
        });

        title.setText(incident.getTitle());
        author.setText(incident.getAuthor());
        date.setText(incident.getDate());
        description.setText(incident.getDescription());
        String bat = Position.getNameLatLon(incident.getLatitude(),incident.getLongitude());
        if(!bat.equals("Rien")){
            batiment.setText(bat);
        }else{
            batiment.setVisibility(View.INVISIBLE);
            Tbatiment.setVisibility(View.INVISIBLE);
        }
        if(incident.getRoom()!=null) {
            salle.setText(incident.getRoom());
        } else {
            Tsalle.setVisibility(View.INVISIBLE);
            salle.setVisibility(View.INVISIBLE);
        }

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detailMap);
        mapFragment.getMapAsync(this);

        if (incident.getImage()!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(incident.getImage(), 0, incident.getImage().length);
            image.setImageBitmap(bitmap);
        }

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
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng position = new LatLng(incident.getLatitude(),incident.getLongitude());
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(position)
                .title(Position.getNameLatLon(incident.getLatitude(),incident.getLongitude())));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refreshProgressBar() {
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setProgress(1+33*incident.getAdvancement(),true);
    }

    public void notificationCall(int sens) {
        NotificationCompat.Builder notification = null;
        if (sens == 0 || sens == 1 || sens ==2) {
            String message = null;
            switch (sens) {
                case 0:
                    message = "Description: " + incident.getDescription() +"\n"+"L'avancement de l'incident diminue";
                    break;
                case 1:
                    message = "Description: " + incident.getDescription() +"\n"+"\n"+"L'avancement de l'incident augmente";
                    break;
                case 2:
                    message = "Description: " + incident.getDescription() +"\n"+"\n"+"L'incident a été supprimé";
                    break;
            }
            notification = (NotificationCompat.Builder) new NotificationCompat.Builder(this, "1")
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setSmallIcon(R.drawable.ic_sublime)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.sublime))
                    .setContentTitle("Info DIPN: l'incident de " + incident.getAuthor())
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(message));

        }


            Intent intent = new Intent(this, IncidentList.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(note, notification.build());
            note ++;
        }


}

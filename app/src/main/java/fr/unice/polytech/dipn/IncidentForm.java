package fr.unice.polytech.dipn;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.unice.polytech.dipn.DataBase.Incident;
import fr.unice.polytech.dipn.DataBase.IncidentViewModel;


public class IncidentForm extends AppCompatActivity implements OnMapReadyCallback {



    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private IncidentViewModel incidentViewModel;
    private GoogleMap googleMap;
    private LocationManager mLocationManager;
    private int PERMISSIONS_REQUEST_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private double userLocationLatitude = 43.616040;
    private double userLocationLongitude = 7.072189;
    private Position positionSpin;

    ImageView imageView;


    @Override
    protected void onCreate (Bundle savedInstanceState){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(IncidentForm.this,R.style.MyAlertDialogStyle);

        builder.setMessage(R.string.popup_message)
                .setTitle(R.string.popup_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                permission();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.show();

        incidentViewModel = ViewModelProviders.of(this).get(IncidentViewModel.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_form);

        final Button save = (Button) findViewById(R.id.save);

        final EditText editAuthor = findViewById((R.id.editAuthor));
        final EditText editTitle = findViewById((R.id.editTitle));

        final SeekBar editEmergency = findViewById(R.id.emergencyBar);
        editEmergency.setMax(2);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        final Spinner editLocalisation = findViewById(R.id.localisationSpinner);
        editLocalisation.setAdapter(new ArrayAdapter<Position>(this, android.R.layout.simple_spinner_item, Position.values()));
        editLocalisation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                positionSpin = (Position)editLocalisation.getSelectedItem();
                LatLng position = new LatLng(positionSpin.getLat(),positionSpin.getLon());
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(position)
                        .title(positionSpin.getName()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18));
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button cameraBtn = (Button) findViewById(R.id.cameraBnt);
        imageView = (ImageView) findViewById(R.id.imageView);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

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
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(currentTime);
                    Incident word = new Incident(title,author,1,positionSpin.getLat(),positionSpin.getLon(),editEmergency.getProgress()+1,editTitle.getText().toString(),formattedDate);
                    incidentViewModel.insert(word);

                    if (editEmergency.getProgress()>=1) {
                        final TwitterSession session = new TwitterSession(new TwitterAuthToken(getString(R.string.com_twitter_sdk_android_ACCESS_KEY), getString(R.string.com_twitter_sdk_android_ACCESS_SECRET)), 985877416857034752L, "pbunice");
                        TwitterCore.getInstance().getSessionManager().setActiveSession(session);

                        final Intent intentTweet = new ComposerActivity.Builder(IncidentForm.this)
                                .session(session)
                                .text("Incident Important:\n"+title+"\nLocalisation: "+editLocalisation.getSelectedItem().toString())
                                .createIntent();
                        startActivity(intentTweet);
                    }

//                    Intent intent = new Intent(IncidentForm.this, IncidentList.class);
//                    startActivity(intent);
                }
                notificationcall();
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
        System.out.println("YOLO LOCATION 1");

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            userLocationLatitude = location.getLatitude();
                            userLocationLongitude = location.getLongitude();
                            System.out.println("YOLO LOCATION 2");
                        }
                        System.out.println("YOLO LOCATION 3");
                    }
                });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng position;
        if(positionSpin != null) {
            position = new LatLng(positionSpin.getLat(),positionSpin.getLon());
        }else{
            position = new LatLng(userLocationLatitude,userLocationLongitude);
        }
        googleMap.addMarker(new MarkerOptions().position(position)
                .title("Polytech Batiment E"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18));

    }

    public void permission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    System.out.println("PERMISSION GRANTED");
                    getCurrentLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    System.out.println("PERMISSION DENIED");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void notificationcall(){

        NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat.Builder(this, "1")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_sublime)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.sublime))
                .setContentTitle("Info DIPN")
                .setContentText("Un nouvel incident a été ajouté");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        if ( bitmap != null) { imageView.setVisibility(View.VISIBLE); }
        imageView.setImageBitmap(bitmap);
    }

}

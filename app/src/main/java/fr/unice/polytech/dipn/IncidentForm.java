package fr.unice.polytech.dipn;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.unice.polytech.dipn.DataBase.Incident;
import fr.unice.polytech.dipn.DataBase.IncidentViewModel;


public class IncidentForm extends AppCompatActivity implements OnMapReadyCallback {


    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private IncidentViewModel incidentViewModel;
    private GoogleMap googleMap;
    private int PERMISSIONS_REQUEST_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private double userLocationLatitude = 0;
    private double userLocationLongitude = 0;
    private double latToSend = 0;
    private double lonToSend = 0;
    private Position positionSpin;
    private String positionRoomSpin = "-----";
    private Bitmap image;
    private static boolean userPosition = true;
    ImageView imageView;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(IncidentForm.this, R.style.MyAlertDialogStyle);

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
                userPosition = false;
            }
        });

        builder.show();

        incidentViewModel = Instance.getInstance().getIncidentViewModel();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_form);

        final Button save = (Button) findViewById(R.id.save);

        final EditText editAuthor = findViewById((R.id.editAuthor));
        final EditText editTitle = findViewById((R.id.editTitle));

        boolean fromTweet = getIntent().getBooleanExtra("fromTweet", false);
        System.out.println("From Tweet flux? " + fromTweet);
        if (fromTweet) {
            String tft = getIntent().getStringExtra("textFromTweet");
            editTitle.setText(tft.split("\n")[0]);
        }

        final SeekBar editEmergency = findViewById(R.id.emergencyBar);
        editEmergency.setMax(2);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        final Spinner editLocalisation = findViewById(R.id.localisationSpinner);
        final Spinner editLocalisationRoom = findViewById(R.id.salleSpinner);
        editLocalisation.setAdapter(new ArrayAdapter<Position>(this, android.R.layout.simple_spinner_item, Position.values()));
        editLocalisation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                positionSpin = (Position) editLocalisation.getSelectedItem();

                editLocalisationRoom.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, PositionRoom.getRoomByBat(positionSpin.getName())));

                LatLng position = new LatLng(positionSpin.getLat(), positionSpin.getLon());
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(position)
                        .title(positionSpin.getName()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18));
                latToSend = positionSpin.getLat();
                lonToSend = positionSpin.getLon();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editLocalisationRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                positionRoomSpin = (String) editLocalisationRoom.getSelectedItem();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ImageView camera = findViewById(R.id.camera);
        imageView = findViewById(R.id.imageView);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
                //dispatchTakePictureIntent();
            }
        });

        ImageView gallery = findViewById(R.id.gallery);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
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
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    byte[] byteArray = null;
                    if (image != null) {
                        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteArray = stream.toByteArray();
                        image.recycle();
                    }
                    Incident word;
                    if (userPosition) {
                        word = new Incident(title, author, 1, userLocationLatitude, userLocationLongitude, null, editEmergency.getProgress() + 1, editTitle.getText().toString(), formattedDate, byteArray);
                    } else {
                        word = new Incident(title, author, 1, latToSend, lonToSend, positionRoomSpin, editEmergency.getProgress() + 1, editTitle.getText().toString(), formattedDate, byteArray);
                    }
                    incidentViewModel.insert(word);

                    if (editEmergency.getProgress() >= 1 && Instance.getInstance().getSession().equals("admin")) {
                        final TwitterSession session = new TwitterSession(new TwitterAuthToken(getString(R.string.com_twitter_sdk_android_ACCESS_KEY), getString(R.string.com_twitter_sdk_android_ACCESS_SECRET)), 985877416857034752L, "pbunice");
                        TwitterCore.getInstance().getSessionManager().setActiveSession(session);

                        if (imageUri != null) {
                            String provider = "fr.unice.polytech.dipn";


                            // grant all three uri permissions!
                            grantUriPermission(provider, imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            grantUriPermission(provider, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                            final Intent intentTweet = new ComposerActivity.Builder(IncidentForm.this)
                                    .session(session)
                                    .text("Incident Important:\n" + title + "\nLocalisation: " + editLocalisation.getSelectedItem().toString())
                                    .image(imageUri)
                                    .createIntent();
                            startActivity(intentTweet);
                        } else {
                            final Intent intentTweet = new ComposerActivity.Builder(IncidentForm.this)
                                    .session(session)
                                    .text("Incident Important:\n" + title + "\nLocalisation: " + editLocalisation.getSelectedItem().toString())
                                    .createIntent();
                            startActivity(intentTweet);
                        }
                    }
                    if (word != null) {
                        notificationCall(word);
                    }

//                    Intent intent = new Intent(IncidentForm.this, IncidentList.class);
//                    startActivity(intent);
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

        Task<Location> locationTask = mFusedLocationClient.getLastLocation().
                addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.clear();
                            googleMap.addMarker(new MarkerOptions().position(latLng)
                                    .title("Votre position"));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                            userLocationLatitude = location.getLatitude();
                            userLocationLongitude = location.getLongitude();
                        }
                    }
                });
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng position;
        if (positionSpin != null && userPosition == false) {
            position = new LatLng(positionSpin.getLat(), positionSpin.getLon());
        } else {
            position = new LatLng(userLocationLatitude, userLocationLongitude);
        }
        googleMap.addMarker(new MarkerOptions().position(position)
                .title("Polytech Batiment E"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 18));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                float distanceMax = 150;

                Location touchLocation = new Location("Touch Location");
                touchLocation.setLatitude(point.latitude);
                touchLocation.setLongitude(point.longitude);

                Location batE = new Location("Batiment E");
                batE.setLongitude(Position.BatimentE.getLon());
                batE.setLatitude(Position.BatimentE.getLat());

                Location luciole = new Location("Luciole");
                luciole.setLatitude(Position.BatimentL.getLat());
                luciole.setLongitude(Position.BatimentL.getLon());

                Location forum = new Location("Forum");
                forum.setLatitude(Position.BatimentF.getLat());
                forum.setLongitude(Position.BatimentF.getLon());

                if (touchLocation.distanceTo(batE) < distanceMax || touchLocation.distanceTo(luciole) < distanceMax || touchLocation.distanceTo(forum) < distanceMax) {
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(point).title("Votre choix"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 18));
                    latToSend = touchLocation.getLatitude();
                    lonToSend = touchLocation.getLongitude();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IncidentForm.this, R.style.MyAlertDialogStyle);

                    builder.setMessage(R.string.popupDistance_message)
                            .setTitle(R.string.popupDistance_title);

                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    builder.show();

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(batE.getLatitude(), batE.getLongitude()), 18));
                }
            }
        });

    }

    public void permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            googleMap.setMyLocationEnabled(true);
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
                    userPosition = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void notificationCall(Incident incident) {
        NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat.Builder(this, "1")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_sublime)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.sublime))
                .setContentTitle("Info DIPN :  nouvel incident ")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Date: " + incident.getDate() + "\n" + "Description: " + incident.getDescription() + "\n" + "Importance: " + showImportance(incident.getImportance())));


        Intent intent = new Intent(this, IncidentList.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification.build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            imageUri = data.getData();
            if (imageUri != null) {
                imageView.setVisibility(View.VISIBLE);
            }
            imageView.setImageURI(imageUri);
        }
        if (resultCode == RESULT_OK && requestCode == 0) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (bitmap != null) {
                imageView.setVisibility(View.VISIBLE);
            }
            imageView.setImageBitmap(bitmap);
            this.image = bitmap;

        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 1);
    }

    private String showImportance(int i) {
        switch (i) {
            case 1:
                return "faible";
            case 2:
                return "moyenne";
            case 3:
                return "forte";
        }
        return "non renseignée";
    }

}


package com.mountaineer.trekking.hikeit.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mountaineer.trekking.hikeit.R;
import com.mountaineer.trekking.hikeit.TrailLatLong;
import com.mountaineer.trekking.hikeit.connector.HTTPImageDownload;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by vijayshrenikraj on 4/21/15.
 */
public class secondScreen extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private boolean color = false;
    private long lastUpdate;
    private GoogleMap GM;
    private ImageView v;
    private String imgUrl;
    Timer myTimer;
    MyTimerTask myTimerTask;
    boolean isRecording = false;
    boolean bookmarkIsChecked = false;
    ArrayList<TrailLatLong> trailLatLong;
    Bundle bund;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondscreen);
        trailLatLong = new ArrayList<>();

        bund = getIntent().getExtras();

        v = (ImageView) findViewById(R.id.secImage);
        TextView imageTitle = (TextView) findViewById(R.id.imageTitle);
        TextView imageAddr = (TextView) findViewById(R.id.imageAddr);

        v.setImageResource(R.drawable.image1);
        imageTitle.setText("" + bund.get("title"));

        imageAddr.setText("" + bund.get("address"));
        imgUrl = "" + bund.get("imageUrl");
        GM = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        GM.addMarker(new MarkerOptions().position(new LatLng(bund.getDouble("latitude"), bund.getDouble("longitude")))

                .draggable(true));
        GM.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(bund.getDouble("latitude"), bund.getDouble("longitude")), 12.0f));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();

        callImageDownload();

        final CheckBox bookmarkButton = (CheckBox) findViewById(R.id.book);
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bookmarkIsChecked) {
                    Toast.makeText(getApplicationContext(), "Added to your Bookmarks", Toast.LENGTH_SHORT).show();
                    //      to  put bookmark details to parse.com       working
                    ParseObject gameScore = new ParseObject("Bookmarks");
                    gameScore.put("title", bund.getString("title"));
                    gameScore.put("address", bund.getString("address"));
                    gameScore.put("imageUrl", bund.getString("imageUrl"));
                    gameScore.put("latitude", ""+bund.getDouble("latitude"));
                    gameScore.put("longitude", ""+bund.getDouble("longitude"));
                    gameScore.put("ratings", bund.getString("ratings"));

                    gameScore.saveInBackground();
                    bookmarkIsChecked = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Removed from your Bookmarks", Toast.LENGTH_SHORT).show();
                    //        to delete boomarks from parse.com         working
                    ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Bookmarks");
                    query2.whereEqualTo("title", bund.getString("title"));
                    query2.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> markers, ParseException e) {
                            if (e == null) {
                                for (int loolObject = 0; loolObject < markers.size(); loolObject++) {
                                    ParseObject tempTest = markers.get(loolObject);
                                    tempTest.deleteInBackground();
                                }
                            } else {
                                // handle Parse Exception here
                            }
                        }
                    });
                    bookmarkIsChecked = false;
//                }
                }
            }
        });


    }

    public void startTrail(View view) {
        Button trailButton = (Button) findViewById(R.id.button3);
        if (myTimer == null) {
            myTimer = new Timer();
            myTimerTask = new MyTimerTask(getApplicationContext());
            myTimer.scheduleAtFixedRate(myTimerTask, 0, 6000);
        }

        if (!isRecording) {
            trailButton.setText("STOP");
            Toast.makeText(getApplicationContext(), "Started Recording", Toast.LENGTH_SHORT).show();
            isRecording = true;

        } else {
            trailButton.setText("START");
            Toast.makeText(getApplicationContext(), "Stopped Recording", Toast.LENGTH_SHORT).show();
            isRecording = false;
            myTimer.cancel();
            myTimer = null;
            addToParse();
        }

    }

    private void addToParse() {
        ParseObject gameScore = new ParseObject("Trails");
        gameScore.put("title", bund.getString("title"));
        gameScore.put("address", bund.getString("address"));
        gameScore.put("imageUrl", bund.getString("imageUrl"));
        String[] trail = new String[trailLatLong.size()];

        for (int i = 0; i < trailLatLong.size(); i++) {
            trail[i] = trailLatLong.get(i).getLatitude() + "|" + trailLatLong.get(i).getLongitude();
            Log.e("trails", trail[i]);
        }
        Log.e("trail size", trail.length + "");
        gameScore.addAllUnique("LatLon", Arrays.asList(trail));
        gameScore.saveInBackground();
    }


    private void doSomething() {
        Toast.makeText(getApplicationContext(), "is it", Toast.LENGTH_SHORT).show();
    }


    private void callImageDownload() {
        HTTPImageDownload down = new HTTPImageDownload();
        down.setConnectionListener(this, "secondscreen");
        try {
            down.connect(imgUrl, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadCompleteBitmap(Bitmap bitmap, int imageID) {
        v.setImageBitmap(bitmap);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            displayAccelerometer(event);
            checkShake(event);
        }
    }

    private void checkShake(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float accelationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if (accelationSquareRoot >= 1.5) {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            //Toast.makeText(this, "Don't shake me!", Toast.LENGTH_SHORT).show();

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            if (color) {
                //Toast.makeText(context, "Don't shake me!", Toast.LENGTH_SHORT).show();

            } else {
                //Toast.makeText(context, "Don't shake me!", Toast.LENGTH_SHORT).show();

            }
            color = !color;
        }
    }

    private void displayAccelerometer(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    class MyTimerTask extends TimerTask {
        private double longitude;
        private double latitude;
        Context parent;

        MyTimerTask(Context c) {
            this.parent = c;

        }

        @Override
        public void run() {
            //get and send location information

            try {
                LocationManager lm = (LocationManager) parent.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Geocoder geocoder = new Geocoder(parent.getApplicationContext(), Locale.getDefault());
                TrailLatLong temp = new TrailLatLong(latitude, longitude);
                trailLatLong.add(temp);
                Log.e("size", "" + trailLatLong.size());
                Log.e("working?", latitude + "");
//            Log.e(Double.toString(longitude),Double.toString(latitude));
//            try {
//                addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            LocationName = addresses.get(0).getAddressLine(0);
            } catch (Exception e) {
                longitude = 0.0;
                latitude = 0.0;
//            LocationName = "Long Beach";
//         Log.e("hello","hello");
            }
        }
    }


}


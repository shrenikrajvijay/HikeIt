package com.mountaineer.trekking.hikeit;

/**
 * Created by vijayshrenikraj on 5/13/15.
 */
public class TrailLatLong {
    double latitude;
    double longitude;

    public  TrailLatLong(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }



    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}


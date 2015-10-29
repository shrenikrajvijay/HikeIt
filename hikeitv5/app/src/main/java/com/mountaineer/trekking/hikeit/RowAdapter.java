package com.mountaineer.trekking.hikeit;

import java.util.ArrayList;

/**
 * Created by vijayshrenikraj on 5/13/15.
 */
public class RowAdapter {
    String title = "";
    ArrayList<Double> latitude;
    ArrayList<Double> longitude;
    String address;
    String imageUrl;

    public ArrayList<Double> getLatitude() {
        return latitude;
    }

    public ArrayList<Double> getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLatitude(ArrayList<Double> latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(ArrayList<Double> longitude) {
        this.longitude = longitude;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

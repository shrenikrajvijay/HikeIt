package com.mountaineer.trekking.hikeit.constants;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by vijayshrenikraj on 4/23/15.
 */
public class Row {
    String title = "";
    boolean imageDownloaded = false;
    String[] address;
    String review = "";
    String ratings = "";
    Bitmap imageBitmap;
    String imageUrl = "";
    double latitude;
    double longitude;
    String description = "";
    ArrayList<String> latlon;


    public Row(String title, boolean image, String[] address, String review, String ratings){
        this.title=title;
        this.imageDownloaded=image;
        this.address=address;
        this.review=review;
        this.ratings=ratings;
    }

    public Row() {
        
    }

    public ArrayList<String> getLatlon() {
        return latlon;
    }

    public void setLatlon(JSONArray lat) {
        this.latlon=new ArrayList<>();
        for (int index = 0; index < lat.length(); index++) {
            try {
                latlon.add(lat.getString(index));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTitle(){
        return title;
    }
    public String getReview(){
        return review;
    }
    public String getRatings(){
        return ratings;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public String getDescription(){
        return description;
    }
    public String[] getAddress(){
        return address;
    }
    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }

    public Bitmap getImage(){
        return imageBitmap;
    }
    public boolean getImageDownloaded(){
        return imageDownloaded;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setReviewCount(String reviewCount) {
        this.review = reviewCount;
    }

    public void setRating(String rating) {
        this.ratings = rating;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
    public void setImageDownloaded(boolean imageBitmap) {
        this.imageDownloaded = imageBitmap;
    }
}

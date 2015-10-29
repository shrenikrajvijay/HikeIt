package com.mountaineer.trekking.hikeit.parsers;

import android.util.Log;

import com.mountaineer.trekking.hikeit.R;
import com.mountaineer.trekking.hikeit.constants.Row;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Charan on 5/3/2015.
 */
public class HomeParser {

    public ArrayList<Row> parse(StringBuilder result) {
        ArrayList<Row> rowList = new ArrayList<Row>();
        try {
            //Json
            try {

                System.out.println(result);
                JSONObject json = new JSONObject(result.toString());
                if (!json.equals("") && json != null) {
                    if (json.has("businesses")) {
                        JSONArray businessesJsonArray = json.getJSONArray("businesses");
                        for (int i = 0; i < businessesJsonArray.length(); i++) {
                            Row row = new Row();
                            JSONObject businessesJsonObject = (JSONObject) businessesJsonArray.get(i);
                            String title = "";
                            int image = R.drawable.image1;
                            if (businessesJsonObject.has("name"))
                                row.setTitle(businessesJsonObject.getString("name"));
                            if (businessesJsonObject.has("review_count"))
                                row.setReviewCount("" + businessesJsonObject.getInt("review_count"));
                            if (businessesJsonObject.has("rating"))
                                row.setRating(businessesJsonObject.getString("rating"));
                            if (businessesJsonObject.has("image_url"))
                                row.setImageUrl(businessesJsonObject.getString("image_url"));
                            if (businessesJsonObject.has("snippet_text"))
                                row.setDescription(businessesJsonObject.getString("snippet_text"));
                            if (businessesJsonObject.has("location")) {
                                JSONObject locationJsonObject = businessesJsonObject.getJSONObject("location");
                                if (locationJsonObject.has("display_address")) {
                                    JSONArray addressArray = locationJsonObject.getJSONArray("display_address");
                                    String[] address = new String[addressArray.length()];
                                    for (int addressLenght = 0; addressLenght < addressArray.length(); addressLenght++) {
                                        address[addressLenght] = addressArray.getString(addressLenght);
                                    }
                                    row.setAddress(address);
                                }
                                if (locationJsonObject.has("coordinate")) {
                                    JSONObject coordinate = locationJsonObject.getJSONObject("coordinate");
                                    if (coordinate.has("latitude")) {
                                        row.setLatitude(coordinate.getDouble("latitude"));
                                    }
                                    if (coordinate.has("longitude")) {
                                        row.setLongitude(coordinate.getDouble("longitude"));
                                    }
                                }
                            }
                            rowList.add(row);
                        }
                    }
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return rowList;
        }catch (Exception e){
            return rowList;
        }
    }
}

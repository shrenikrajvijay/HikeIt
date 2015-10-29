package com.mountaineer.trekking.hikeit.screens;

import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mountaineer.trekking.hikeit.R;
import com.mountaineer.trekking.hikeit.adapters.listAdapter;
import com.mountaineer.trekking.hikeit.connector.HTTPImageDownload;
import com.mountaineer.trekking.hikeit.constants.Row;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkFragment extends ListFragment {
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    private listAdapter lst;
    ArrayList<Row> objList;
    List<Address> addresses;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout_1, container, false);
        objList = new ArrayList<Row>();

        lst = new listAdapter(inflater.getContext(), objList);
        setListAdapter(lst);


        //to retrieve all the details from parse.com    working
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Bookmarks");
        try {
            List<ParseObject> markers = query.find();
            for (int loopIndex = 0; loopIndex < markers.size(); loopIndex++) {
                Row row = new Row();
                row.setTitle((markers.get(loopIndex).getString("title") != null) ? markers.get(loopIndex).getString("title") : "");
                String[] str = {(markers.get(loopIndex).getString("address") != null) ? markers.get(loopIndex).getString("address") : ""};
                row.setAddress(str);
                row.setImageUrl((markers.get(loopIndex).getString("imageUrl") != null) ? markers.get(loopIndex).getString("imageUrl") : "");
                row.setLatitude((markers.get(loopIndex).getString("latitude") != null) ? Double.parseDouble(markers.get(loopIndex).getString("latitude")) : 0.0);
                row.setLongitude((markers.get(loopIndex).getString("longitude") != null) ? Double.parseDouble(markers.get(loopIndex).getString("longitude")) : 0.0);
                row.setRating((markers.get(loopIndex).getNumber("ratings") != null) ? markers.get(loopIndex).getString("ratings") : "0.0");
                objList.add(row);
            }
        } catch (com.parse.ParseException e){

        }
        callImageDownload();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int i, long id) {
        super.onListItemClick(l, v, i, id);
        Intent in = new Intent(getActivity(), secondScreen.class);
        Row tempData = objList.get(i);
        in.putExtra("title", tempData.getTitle());
        in.putExtra("imageDownloaded", tempData.getImageDownloaded());
        String str1 = "";
        for (int index = 0; index < tempData.getAddress().length; index++) {
            str1 += tempData.getAddress()[index] + ",";
        }
        in.putExtra("address", str1);
        in.putExtra("ratings", tempData.getRatings());
        in.putExtra("imageBitmap", tempData.getImage());
        in.putExtra("imageUrl", tempData.getImageUrl());
        in.putExtra("latitude", tempData.getLatitude());
        in.putExtra("longitude", tempData.getLongitude());
        startActivity(in);
    }

    private void callImageDownload() {
        HTTPImageDownload down = new HTTPImageDownload();
        down.setConnectionListener(this, "bookmarkfragment");
        try {
            for (int loopImgReq = 0; loopImgReq < objList.size(); loopImgReq++) {
                down.connect(((Row) objList.get(loopImgReq)).getImageUrl(), loopImgReq);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadCompleteBitmap(Bitmap bitmap, int imageID) {
        ((Row) objList.get(imageID)).setImage(bitmap);
        lst.notifyDataSetChanged();
    }
}

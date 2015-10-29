package com.mountaineer.trekking.hikeit.screens;

/**
 * Created by vijayshrenikraj on 5/3/15.
 */

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mountaineer.trekking.hikeit.R;
import com.mountaineer.trekking.hikeit.adapters.listAdapter;
import com.mountaineer.trekking.hikeit.connector.HTTPImageDownload;
import com.mountaineer.trekking.hikeit.connector.HttpConnection;
import com.mountaineer.trekking.hikeit.constants.Row;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentOne extends ListFragment {
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private static FragmentOne fragmentInstance;

    private String LocationName = "Long Beach";
    private listAdapter lst;
    private double longitude;
    private double latitude;
    String cityName;
    ArrayList<Row> objList;
    static FragmentOne fragment;
    List<Address> addresses;
    public FragmentOne() {
        fragmentInstance= this;
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout_1, container, false);

        ArrayList<Row> objList = new ArrayList<Row>();
        lst = new listAdapter(inflater.getContext(), objList);
        setListAdapter(lst);

        try {
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            LocationName = addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
            longitude = 0.0;
            latitude = 0.0;
            LocationName = "Long Beach";
        }
//        ctrList= (ListView) view.findViewById(R.id.listView);
//        lstAdptr = new listAdapter(getActivity(), objList);
//        ctrList.setListAdapter(lstAdptr);
//        ctrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent in=new Intent(getActivity(),secondScreen.class);
//                //in.putExtra("position",i);  //incase needed
//                //in.putExtra("image",images_array[i]);
//                //in.putExtra("title",images_names[i]);
//                //in.putExtra("address",images_address_full[i]);
//                startActivity(in);
//            }
//        });

        callHttpTemp(LocationName);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void callHttpTemp(String query) {
        try {
            LocationName = query;
            String url = URLEncoder.encode(LocationName, "UTF-8");
            String actUrl = "http://api.yelp.com/v2/search?term=hike&location=" + url;
            HttpConnection ht = new HttpConnection();
            ht.setConnectionListener(this);
            ht.connect(actUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadComplete(ArrayList<Row> result) {
        if (result.size() > 0) {
            objList = result;
            lst.getData().clear();
            lst.getData().addAll(result);
            lst.notifyDataSetChanged();
        } else {
            Toast.makeText(view.getContext(), "Error, Check connection!", Toast.LENGTH_SHORT);
        }
        callImageDownload();
    }

    private void callImageDownload() {
        HTTPImageDownload down = new HTTPImageDownload();
        down.setConnectionListener(this, "fragmentone");
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

    @Override
    public void onListItemClick(ListView l, View v, int i, long id) {
        super.onListItemClick(l, v, i, id);
        Intent in = new Intent(getActivity(), secondScreen.class);
        Row tempData = objList.get(i);
        in.putExtra("title", tempData.getTitle());
        in.putExtra("imageDownloaded", tempData.getImageDownloaded());
        String str1 = "";
        for (int index = 0; index <tempData.getAddress().length; index++) {
            str1+=tempData.getAddress()[index]+",";
        }
        in.putExtra("address", str1);
        in.putExtra("review", tempData.getReview());
        in.putExtra("ratings", tempData.getRatings());
        in.putExtra("imageBitmap", tempData.getImage());
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        tempData.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        in.putExtra("imageBitmap", byteArray);
        in.putExtra("imageUrl", tempData.getImageUrl());
        in.putExtra("latitude", tempData.getLatitude());
        in.putExtra("longitude", tempData.getLongitude());
        in.putExtra("description", tempData.getDescription());
        startActivity(in);
    }

    public static void callHttp(String query) {
        fragmentInstance.callHttpTemp(query);
    }
}

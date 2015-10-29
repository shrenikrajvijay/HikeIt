package com.mountaineer.trekking.hikeit;

import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mountaineer.trekking.hikeit.connector.HTTPImageDownload;
import com.mountaineer.trekking.hikeit.constants.Row;
import com.mountaineer.trekking.hikeit.screens.FragmentOne;
import com.mountaineer.trekking.hikeit.screens.secondScreen;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijayshrenikraj on 5/13/15.
 */
public class FragmentMyTrail extends ListFragment{
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private static FragmentOne fragmentInstance;

    private String LocationName = "Long Beach";
    private TrailAdapter lst;
    private double longitude;
    private double latitude;
    String cityName;
    ArrayList<Row> objList;
    List<Address> addresses;
    public FragmentMyTrail() {

    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout_1, container, false);

        objList = new ArrayList<Row>();


        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Trails");
        try {
            List<ParseObject> markers = query1.find();
            for (int loopIndex = 0; loopIndex < markers.size(); loopIndex++) {
                Row row = new Row();
                row.setTitle(markers.get(loopIndex).getString("title"));
                String[] ssttr = {markers.get(loopIndex).getString("address")};
                row.setAddress(ssttr);
                row.setImageUrl(markers.get(loopIndex).getString("imageUrl"));
                row.setLatlon(markers.get(loopIndex).getJSONArray("LatLon"));
//                        Toast.makeText(getActivity(), row.getTitle(),Toast.LENGTH_SHORT).show();

                objList.add(row);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lst = new TrailAdapter(inflater.getContext(), objList);
        setListAdapter(lst);
        callImageDownload();

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void downloadCompleteBitmap(Bitmap bitmap, int imageID) {
        ((Row) objList.get(imageID)).setImage(bitmap);
        lst.notifyDataSetChanged();
    }
    private void callImageDownload() {
        HTTPImageDownload down = new HTTPImageDownload();
        down.setConnectionListener(this, "trailfragment");
        try {
            for (int loopImgReq = 0; loopImgReq < objList.size(); loopImgReq++) {
                down.connect(((Row) objList.get(loopImgReq)).getImageUrl(), loopImgReq);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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


}




























//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v=inflater.inflate(R.layout.layout_mytrail,container,false);
//        String [] ar=new String[]{"hello","world"};
//        ListView fragmentMyTrailListViewHandle= (ListView) v.findViewById(R.id.FragmentMyTrailListViewHandle);
//        TrailAdapter adapt=new TrailAdapter(getActivity(),)
//        fragmentMyTrailListViewHandle.setAdapter(adpt);
//    return v;
//    }
//}

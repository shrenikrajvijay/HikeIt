package com.mountaineer.trekking.hikeit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mountaineer.trekking.hikeit.constants.Row;

import java.util.ArrayList;

/**
 * Created by vijayshrenikraj on 4/10/15.
 */


public class TrailAdapter extends BaseAdapter {

    private ArrayList<Row> mData;
    private Context context;


    public TrailAdapter(Context context, final ArrayList<Row> mData) {
        this.context = context;
        this.mData = mData;

    }

    public ArrayList<Row> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mData != null ? mData.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        // just returning position as id here, could be the id of your model object instead
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View image = convertView;
        LayoutInflater LInflator;
        LInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        image = LInflator.inflate(R.layout.listview, parent, false);

        ImageView ctrListImage = (ImageView) image.findViewById(R.id.listImage);
        TextView ctrListText = (TextView) image.findViewById(R.id.listText);
        TextView ctrListName = (TextView) image.findViewById(R.id.address);
        TextView ctrListReview = (TextView) image.findViewById(R.id.imageAddr);
        RatingBar rb = (RatingBar) image.findViewById(R.id.rtbHighScore);
        Row single = (Row) mData.get(position);

        try {
            if(single.getImageDownloaded()) {
                ctrListImage.setImageResource(R.drawable.image10);
            }else{
                try {
                    ctrListImage.setImageBitmap(single.getImage());
                }catch (Exception e){

                }
            }
            ctrListText.setText(single.getTitle());
            String[] addressStrings = single.getAddress();
            if(addressStrings.length>0){
                ctrListName.setText(addressStrings[addressStrings.length-1]);
            }
            else{
                ctrListName.setText("NA");
            }

            ctrListReview.setText(single.getReview());
            int rating = (int) Double.parseDouble(single.getRatings());
            rb.setNumStars(rating);
        } catch (Exception e){

        }
        return image;
    }
}



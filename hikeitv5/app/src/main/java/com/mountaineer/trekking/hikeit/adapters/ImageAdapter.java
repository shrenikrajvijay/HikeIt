package com.mountaineer.trekking.hikeit.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mountaineer.trekking.hikeit.R;

/**
 * Created by Charan on 5/4/2015.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int imageWidth = (width-6)/3;
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(mContext, "You Clicked ", Toast.LENGTH_LONG).show();
            }
        });
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.camera, R.drawable.hike1,
            R.drawable.hike2, R.drawable.hike3,
            R.drawable.hike4, R.drawable.hike5,
            R.drawable.hike6, R.drawable.hike7,
            R.drawable.hike8, R.drawable.hike9,
            R.drawable.hike10, R.drawable.hike1,
            R.drawable.image2, R.drawable.image5,
            R.drawable.image6, R.drawable.image10,
            R.drawable.image7, R.drawable.image8,

    };
}

//public class ImageAdapter extends BaseAdapter {
//    private Context mContext;
//
//    // Keep all Images in array
//    public Integer[] mThumbIds = {
//            R.drawable.image1, R.drawable.image2,
//            R.drawable.image3, R.drawable.image3,
//            R.drawable.image4, R.drawable.image5,
//            R.drawable.image6, R.drawable.image7,
//            R.drawable.image8, R.drawable.image9,
//            R.drawable.image10, R.drawable.image1
//    };
//
//    // Constructor
//    public ImageAdapter(Context c){
//        mContext = c;
//    }
//
//    @Override
//    public int getCount() {
//        return mThumbIds.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mThumbIds[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        /*ImageView imageView= new ImageView(mContext);
//        imageView.setImageResource(mThumbIds[position]);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(70, 70));*/
//
//        ImageView imageView;
//        if (convertView == null) {
//            // if it's not recycled, initialize some attributes
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
//        } else {
//            imageView = (ImageView) convertView;
//        }
//
//        imageView.setImageResource(mThumbIds[position]);
//
//        return imageView;
//    }
//
//}
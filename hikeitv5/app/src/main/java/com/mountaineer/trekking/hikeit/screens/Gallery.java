package com.mountaineer.trekking.hikeit.screens;

import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.mountaineer.trekking.hikeit.R;
import com.mountaineer.trekking.hikeit.adapters.ImageAdapter;

public class Gallery extends Fragment {

    //ImageView ivIcon;
    //TextView tvItemName;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    View rootView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {;
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        } catch (InflateException e) {
        }
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
        ImageAdapter customGridAdapter = new ImageAdapter(getActivity());
        gridView.setAdapter(customGridAdapter);

        //ivIcon = (ImageView) view.findViewById(R.id.frag3_icon);
        //tvItemName = (TextView) view.findViewById(R.id.frag3_text);

        //tvItemName.setText(getArguments().getString(ITEM_NAME));
        //ivIcon.setImageDrawable(view.getResources().getDrawable(
        //getArguments().getInt(IMAGE_RESOURCE_ID);
        return rootView;
    }
}

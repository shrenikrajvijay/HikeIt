package com.mountaineer.trekking.hikeit.screens;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mountaineer.trekking.hikeit.R;

public class FragmentTwo extends Fragment {

    //ImageView ivIcon;
    TextView tvItemName;
    Button reportBug;
    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private View view;

    public FragmentTwo()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_layout_2,container, false);

        //ivIcon=(ImageView)view.findViewById(R.id.frag2_icon);
        tvItemName=(TextView)view.findViewById(R.id.frag2_text);

        tvItemName.setText(getArguments().getString(ITEM_NAME));
        //ivIcon.setImageDrawable(view.getResources().getDrawable(                getArguments().getInt(IMAGE_RESOURCE_ID)));
        reportBug = (Button) view.findViewById(R.id.bugReportBtn);
        reportBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"char2674@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Bug report");
                if(tvItemName.getText() != "" )i.putExtra(Intent.EXTRA_TEXT   , tvItemName.getText());
                else Toast.makeText(view.getContext(), "Bug has been reported.", Toast.LENGTH_SHORT).show();
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(view.getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
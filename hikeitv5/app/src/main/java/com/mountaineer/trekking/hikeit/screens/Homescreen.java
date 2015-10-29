package com.mountaineer.trekking.hikeit.screens;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.mountaineer.trekking.hikeit.R;
import com.mountaineer.trekking.hikeit.adapters.listAdapter;
import com.mountaineer.trekking.hikeit.constants.Row;

import java.util.ArrayList;


public class Homescreen extends Activity {
    int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6};
    private String LocationName = "San+Francisco";
    private ListView ctrList;
    private listAdapter lstAdptr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);


//        //initializing the arraylist
//
//        Resources res=getResources();
//
//        ArrayList<Row> objList = new ArrayList<Row>();
//        ctrList= (ListView) findViewById(R.id.listView);
//        lstAdptr = new listAdapter(this, objList);
//        ctrList.setAdapter(lstAdptr);
//        ctrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent in=new Intent(Homescreen.this,secondScreen.class);
//                //in.putExtra("position",i);  //incase needed
//                //in.putExtra("image",images_array[i]);
//                //in.putExtra("title",images_names[i]);
//                //in.putExtra("address",images_address_full[i]);
//                startActivity(in);
//            }
//        });
//
//        HttpConnection ht = new HttpConnection();
//        ht.setConnectionListener(this);
//        try {
//            ht.connect("http://api.yelp.com/v2/search?term=hike&location="+LocationName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public void downloadComplete(ArrayList<Row> result) {
//        Log.d("Testing:", "" + result.size());
//        lstAdptr.getData().clear();
//        lstAdptr.getData().addAll(result);
//        lstAdptr.notifyDataSetChanged();
    }
}

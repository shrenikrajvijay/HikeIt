package com.mountaineer.trekking.hikeit;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mountaineer.trekking.hikeit.adapters.CustomDrawerAdapter;
import com.mountaineer.trekking.hikeit.connector.DrawerItem;
import com.mountaineer.trekking.hikeit.screens.BookmarkFragment;
import com.mountaineer.trekking.hikeit.screens.FragmentOne;
import com.mountaineer.trekking.hikeit.screens.FragmentThree;
import com.mountaineer.trekking.hikeit.screens.FragmentTwo;
import com.mountaineer.trekking.hikeit.screens.Fragment_Search;
import com.mountaineer.trekking.hikeit.screens.Gallery;
import com.parse.Parse;

import java.util.ArrayList;
import java.util.List;

public class main extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
//
        Parse.initialize(this, "HHb0JI1cDfFbcM03MqGRRySWMJka0oBxUM5ZWxUg", "hQNR2lpiRgxUJbN4oBrJdbN2MDDFlWCuvA4YKyuL");

//      to  put bookmark details to parse.com       working
//        ParseObject gameScore = new ParseObject("Bookmarks");
//        gameScore.put("title", "Kiran1");
//        gameScore.put("address", "long beach");
//        gameScore.put("imageUrl", "image");
//        gameScore.put("latitude", 34348.234);
//        gameScore.put("longitude", 4324.242);
//        gameScore.saveInBackground();

//        to delete boomarks from parse.com         working
//        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Bookmarks");
//        query2.whereEqualTo("title", "Kiran2");
//        query2.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> markers, ParseException e) {
//                if (e == null) {
//                    for (int loolObject = 0; loolObject < markers.size(); loolObject++) {
//                        ParseObject tempTest = markers.get(loolObject);
//                        tempTest.deleteInBackground();
//                    }
//                } else {
//                    // handle Parse Exception here
//                }
//            }
//        });

        //to retrieve all the details from parse.com    working
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Bookmarks");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> markers, ParseException e) {
//                if (e == null) {
//                    // your logic here
//                    for (int loopIndex = 0; loopIndex < markers.size(); loopIndex++) {
//                        Row row = new Row();
//                        row.setTitle(markers.get(loopIndex).getString("title"));
//                        row.setTitle(markers.get(loopIndex).getString("address"));
//                        row.setTitle(markers.get(loopIndex).getString("imageUrl"));
//                        row.setLatitude((Double) markers.get(loopIndex).getNumber("latitude"));
//                        row.setLongitude((Double) markers.get(loopIndex).getNumber("longitude"));
//                    }
//                } else {
//                    // handle Parse Exception here
//                }
//            }
//        });

//        ParseLoginBuilder builder = new ParseLoginBuilder(main.this);
//        startActivityForResult(builder.build(), 0);
        /*
         * Showing splash screen with a timer. This will be useful when you
         * want to show case your app logo / company
         */
//        new Handler().postDelayed(new Runnable() {
//
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                Intent i = new Intent(main.this, Homescreen.class);
//                startActivity(i);
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);

        // Initializing
        dataList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Add Drawer Item to dataList
        dataList.add(new DrawerItem("Nearby", R.drawable.ic_action_email));
        //dataList.add(new DrawerItem("Search", R.drawable.ic_action_good));
        //dataList.add(new DrawerItem("Profile", R.drawable.ic_action_gamepad));
        dataList.add(new DrawerItem("Bookmarks", R.drawable.ic_action_labels));
        //dataList.add(new DrawerItem("Search", R.drawable.ic_action_search));
        //dataList.add(new DrawerItem("Events", R.drawable.ic_action_cloud));
        dataList.add(new DrawerItem("Gallery", R.drawable.ic_action_camera));
        dataList.add(new DrawerItem("Check-Ins", R.drawable.ic_action_video));
        dataList.add(new DrawerItem("Share", R.drawable.ic_action_group));
        //dataList.add(new DrawerItem("Sync Calendar", R.drawable.ic_action_import_export));
        dataList.add(new DrawerItem("Settings", R.drawable.ic_action_about));
        //dataList.add(new DrawerItem("About Us", R.drawable.ic_action_settings));
        dataList.add(new DrawerItem("Report Bug", R.drawable.ic_action_help));
        dataList.add(new DrawerItem("My Trails", R.drawable.ic_action_good));

        adapter = new CustomDrawerAdapter(this, R.layout.custom_layout,
                dataList);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            SelectItem(0);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void SelectItem(int possition) {

        Fragment fragment = null;
        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new FragmentOne();
                args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;
            case 1:
                fragment = new BookmarkFragment();
                args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;
            case 2:
                fragment = new Gallery();
                args.putString(Gallery.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(Gallery.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;
            case 3:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;
            case 4:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;
            case 5:
                fragment = new FragmentThree();
                args.putString(FragmentThree.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;
            case 6:

                fragment = new FragmentTwo();
                args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;
            case 7:

                fragment = new FragmentMyTrail();
                args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
                        .getImgResID());
                break;
            default:
                break;
        }
        if (possition == 0) {
            FragmentManager frgManager = getFragmentManager();
            Fragment search = new Fragment_Search();
            frgManager.beginTransaction().replace(R.id.search_input_layout, search, "first")
                    .commit();
        } else {
            Fragment f = getFragmentManager().findFragmentByTag("first");
            if (f != null) {
                FragmentManager manager = this.getFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(f);
                trans.commit();
            }
        }

        fragment.setArguments(args);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                .commit();

        mDrawerList.setItemChecked(possition, true);
        setTitle(dataList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    // public static void checkConnection() {
//        boolean connectionOk = false;
//        ConnectivityManager connMgr = (ConnectivityManager) connectionListner.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            // fetch data
//            //Log.d("Testing", "Passed pt 1");
//            connectionOk = true;
//        } else {
//            //Toast.makeText(connectionListner, "Connection is not available.", Toast.LENGTH_LONG);
//            connectionOk = false;
//        }
//        return connectionOk;
    //  }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);

        }
    }

    public class drawItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);


        }
    }

}
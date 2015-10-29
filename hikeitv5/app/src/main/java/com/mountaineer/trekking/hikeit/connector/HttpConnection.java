package com.mountaineer.trekking.hikeit.connector;

import android.os.AsyncTask;
import android.util.Log;

import com.mountaineer.trekking.hikeit.screens.FragmentOne;
import com.mountaineer.trekking.hikeit.constants.Row;
import com.mountaineer.trekking.hikeit.constants.constants;
import com.mountaineer.trekking.hikeit.parsers.HomeParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by Charan on 4/24/2015.
 */
public class HttpConnection {
    FragmentOne connectionListner;
    private ArrayList<Row> arrayList = new ArrayList<Row>();


    public void setConnectionListener(FragmentOne connectionListner) {
        this.connectionListner = connectionListner;
    }


    public void connect(String urlString) throws IOException {
//        ConnectivityManager connMgr = (ConnectivityManager) connectionListner.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            //Log.d("Testing", "Passed pt 1");
            new DownloadWebpageTask().execute(urlString);
//        } else {
//            //Toast.makeText(connectionListner, "Connection is not available.", Toast.LENGTH_LONG);
//        }
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                //Log.d("Testing", "Passed pt 2");
                return getDownloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(connectionListner, result, Toast.LENGTH_SHORT);
            Log.d("Point 1", "--------------------------------Cleared");
            connectionListner.downloadComplete(arrayList);
        }
    }

    private String getDownloadUrl(String myUrl) throws IOException {
        InputStream is = null;
       // Log.d("Testing", "Passed pt 3");
        // Only display the first 500 characters of the retrieved
        // web page content.
        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query

            OAuthConsumer consumer = new DefaultOAuthConsumer(constants.CONSUMER_KEY, constants.CONSUMER_SECRET);
            consumer.setTokenWithSecret(constants.ACCESS_TOKEN, constants.TOKEN_SECRET);
            // sign the request
            try {
                consumer.sign(conn);
            } catch (OAuthMessageSignerException e) {
                e.printStackTrace();
            } catch (OAuthExpectationFailedException e) {
                e.printStackTrace();
            } catch (OAuthCommunicationException e) {
                e.printStackTrace();
            }
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d("DEBUG_TAG", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader ir = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String read = ir.readLine();
            while (read != null) {
                //System.out.println(read);
                sb.append(read);
                read = ir.readLine();
            }
            //String contentAsString = sb.toString();
            //Log.d("ready on not here", contentAsString);
            HomeParser hm = new HomeParser();
            arrayList = hm.parse(sb);
            return "Parsing Done";

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}

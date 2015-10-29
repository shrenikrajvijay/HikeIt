package com.mountaineer.trekking.hikeit.connector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.mountaineer.trekking.hikeit.FragmentMyTrail;
import com.mountaineer.trekking.hikeit.screens.BookmarkFragment;
import com.mountaineer.trekking.hikeit.screens.FragmentOne;
import com.mountaineer.trekking.hikeit.screens.secondScreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vijayshrenikraj on 5/4/15.
 */
public class HTTPImageDownload {
    FragmentOne connectionListner1;
    secondScreen connectionListner2;
    FragmentMyTrail connectionListner3;
    BookmarkFragment connectionListner4;
String className;
    Bitmap bitmap;
    public void setConnectionListener(FragmentOne connectionListner, String className) {
        this.connectionListner1 = connectionListner;
        this.className = className;
    }
    public void setConnectionListener(secondScreen connectionListner,  String className) {
        this.connectionListner2 = connectionListner;
        this.className = className;
    }
    public void setConnectionListener(FragmentMyTrail connectionListner,  String className) {
        this.connectionListner3 = connectionListner;
        this.className = className;
    }
    public void setConnectionListener(BookmarkFragment connectionListner,  String className) {
        this.connectionListner4 = connectionListner;
        this.className = className;
    }
    public void connect(String urlString, int imageID) throws IOException {
        ImageHolder imageDetails = new ImageHolder(urlString, imageID);
        new DownloadWebpageTask().execute(imageDetails);
    }

    private class DownloadWebpageTask extends AsyncTask<ImageHolder, Void, ImageHolder> {
        @Override
        protected ImageHolder doInBackground(ImageHolder... params) {
            ImageHolder imageDetails = params[0];
            try {
                imageDetails.image = getDownloadUrl(imageDetails.url);
            } catch (IOException e) {
                //"Unable to retrieve web page. URL may be invalid.";
            }
            return imageDetails;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ImageHolder result) {
            //Toast.makeText(connectionListner, result, Toast.LENGTH_SHORT);
            if(className == "fragmentone"){
                connectionListner1.downloadCompleteBitmap(result.image, result.id);
            }  else if(className == "trailfragment") {
                connectionListner3.downloadCompleteBitmap(result.image, result.id);

            } else if (className == "bookmarkfragment") {
                connectionListner4.downloadCompleteBitmap(result.image, result.id);
            }    else
            {
                connectionListner2.downloadCompleteBitmap(result.image, result.id);
            }
        }
    }

    private Bitmap getDownloadUrl(String myUrl) throws IOException {
        InputStream is = null;
        // Log.d("Testing", "Passed pt 3");
        // Only display the first 500 characters of the retrieved
        // web page content.
        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d("DEBUG_TAG", "The response is: " + response);
            is = conn.getInputStream();
            bitmap= BitmapFactory.decodeStream(is);

            return bitmap;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private class CustomWrap {
        public String url;
    }

    private class ImageHolder {
        public String url;
        public Bitmap image;
        public int id;

        public ImageHolder(String urlString, int imageID) {
            this.url = urlString;
            this.id = imageID;
        }
    }
}

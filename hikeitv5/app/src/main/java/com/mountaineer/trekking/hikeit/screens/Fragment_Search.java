package com.mountaineer.trekking.hikeit.screens;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mountaineer.trekking.hikeit.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by vijayshrenikraj on 5/5/15.
 */
public class Fragment_Search extends Fragment {
    private Context c;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_search_layout, container, false);
        final EditText inp= (EditText) v.findViewById(R.id.userInputText);
        inp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String inpTerm=inp.getText().toString();
                    String query="";
                    //FragmentOne.LocationName=inpTerm;
                    //Toast.makeText(getActivity(),inpTerm,Toast.LENGTH_LONG).show();
                    try {
                         query = URLEncoder.encode(inpTerm, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    FragmentOne.callHttp(query);
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });


        return v;
    }

}

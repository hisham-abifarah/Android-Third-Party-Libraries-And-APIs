package com.inci.androidapisandthirdpartylibrairies.Volley;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inci.androidapisandthirdpartylibrairies.R;

import org.json.JSONObject;

/**
 * Created by Hisham Abi Farah on 1/29/2017.
 */

public class VolleyBasicExample extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView txtResponse= null;
    String url = "http://headers.jsontest.com/";
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResponse= (TextView)findViewById(R.id.textView);
        resources = getResources();
        this.volleyJsonObjectRequest(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;

        //// TODO: 1/31/2017 add selected menu item
    }

    public void volleyJsonObjectRequest(String url){

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response.toString()) ;
                        String textResponse = resources.getString(R.string.textview_response,response.toString());
                        txtResponse.setText(textResponse);
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        String textResponse = resources.getString(R.string.textview_response,error.toString());
                        txtResponse.setText(textResponse);
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }
                });
        queue.add(jsObjRequest);
    }
}
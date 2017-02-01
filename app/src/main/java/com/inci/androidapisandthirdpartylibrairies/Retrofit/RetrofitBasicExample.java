package com.inci.androidapisandthirdpartylibrairies.Retrofit;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inci.androidapisandthirdpartylibrairies.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hisham Abi Farah on 1/29/2017.
 */

public class RetrofitBasicExample extends AppCompatActivity  {
    private static final String TAG = "RetrofitBasicExample";
    private TextView txtResponse;
    Resources mResources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_main);
        txtResponse= (TextView)findViewById(R.id.textView);
        mResources = getResources();
        this.loadUser();
    }

    public void loadUser(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GithubAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GithubAPI githubUserAPI = retrofit.create(GithubAPI.class);

        Call<GithubUser> callUser = githubUserAPI.getUser("hisham-abifarah");
        //asynchronous call
        callUser.enqueue(new Callback<GithubUser>() {

        @Override
        public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
            int code = response.code();
            if (code == 200) {
                GithubUser user = response.body();
                Log.d(TAG, "onResponse: " + "Username: " + user.login + "\t" + " User Id: " + user.id );
                String textResponse = mResources.getString(R.string.textview_response, user.id + user.login);
                txtResponse.setText(textResponse);
            } else {
                Log.d(TAG, "onResponse: Failed to get User Details");
            }
        }
        @Override
        public void onFailure(Call<GithubUser> call, Throwable t) {
            Log.d(TAG, "onFailure: Failed to get User Details");
        }});
    }
}

    class GithubUser {

     String login;
     String id;
}
     interface GithubAPI {
        String ENDPOINT = "https://api.github.com";

        @GET("/users/{user}")
        Call<GithubUser> getUser(@Path("user") String user);
}
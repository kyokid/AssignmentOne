package com.example.havh.assignment_one;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.havh.assignment_one.adapter.PhotosAdapter;
import com.example.havh.assignment_one.model.InstagramPhoto;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";

    private ArrayList<InstagramPhoto> photos;

    private PhotosAdapter adapterPhoto;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photos = new ArrayList<>();

        adapterPhoto = new PhotosAdapter(this, photos);

        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);

        lvPhotos.setAdapter(adapterPhoto);

        final String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;


        fetchPopularPhotos();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                AsyncHttpClient client = new AsyncHttpClient();

                client.get(url, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        adapterPhoto.clear();
                        JSONArray photosJSON = null;

                        try {
                            photosJSON = response.getJSONArray("data");

                            for (int i = 0; i < photosJSON.length(); i++) {
                                JSONObject photoJSON = photosJSON.getJSONObject(i);
                                InstagramPhoto photo = new InstagramPhoto();

                                photo.avatarUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                                photo.username = photoJSON.getJSONObject("user").getString("username");

                                photo.caption = photoJSON.getJSONObject("caption").getString("text");

                                photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");

                                photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");

                                photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

                                photos.add(photo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapterPhoto.addAll(photos);
                        swipeContainer.setRefreshing(false);
                        adapterPhoto.notifyDataSetChanged();
                    }
                });
            }


        });
        swipeContainer.setRefreshing(false);

    }


    private void fetchPopularPhotos() {

        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        final AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSON = null;

                try {
                    photosJSON = response.getJSONArray("data");

                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();

                        photo.avatarUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.username = photoJSON.getJSONObject("user").getString("username");

                        photo.caption = photoJSON.getJSONObject("caption").getString("text");

                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");

                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");

                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapterPhoto.notifyDataSetChanged();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


            }
        });

    }
}
